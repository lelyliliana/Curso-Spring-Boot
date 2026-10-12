package com.lelyliliana.productos;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import tools.jackson.databind.json.JsonMapper;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Sin @Transactional en el test: las llamadas al servicio confirman/revierten realmente.
@SpringBootTest(properties = {"app.editor-password=prueba-editor", "app.lector-password=prueba-lector",
    "spring.datasource.url=jdbc:h2:mem:pedidos-tests;MODE=PostgreSQL;DB_CLOSE_DELAY=-1"})
@AutoConfigureMockMvc
class PedidoIntegrationTest {
    @Autowired PedidoService servicio;
    @Autowired PedidoRepository pedidos;
    @Autowired ProductoService productos;
    @Autowired CategoriaRepository categorias;
    @Autowired JdbcTemplate jdbc;
    @Autowired MockMvc mvc;
    @Autowired JsonMapper mapper;
    @Autowired PlatformTransactionManager tx;

    private ProductoResponse producto(String sku) {
        return productos.crear(new ProductoRequest(sku, "Libro de prueba", new BigDecimal("10.00"),
            categorias.findAll().getFirst().getId()));
    }

    private PedidoResponse borrador(ProductoResponse producto) {
        return servicio.crear(new PedidoRequest(List.of(new DetalleRequest(producto.id(), 2))));
    }

    @Test void precioHistorico() {
        var producto = producto("PED-HIST");
        var borrador = borrador(producto);
        assertNull(borrador.detalles().getFirst().precioUnitario());
        productos.actualizar(producto.id(), new ProductoUpdateRequest(producto.sku(), producto.nombre(),
            new BigDecimal("15.00"), producto.categoriaId(), producto.version()));
        var confirmado = servicio.confirmar(borrador.id(), new VersionRequest(borrador.version()));
        assertEquals("CONFIRMADO", confirmado.estado());
        assertEquals(0, new BigDecimal("30.00").compareTo(confirmado.total()));
        var actual = productos.buscar(producto.id());
        productos.actualizar(producto.id(), new ProductoUpdateRequest(actual.sku(), actual.nombre(),
            new BigDecimal("99.00"), actual.categoriaId(), actual.version()));
        assertEquals(0, new BigDecimal("30.00").compareTo(servicio.buscar(borrador.id()).total()));
    }

    @Test void versionAntiguaNoCambiaPedido() {
        var pedido = borrador(producto("PED-VER"));
        servicio.confirmar(pedido.id(), new VersionRequest(pedido.version()));
        assertThrows(Conflicto.class, () -> servicio.cancelar(pedido.id(), new VersionRequest(pedido.version())));
        assertEquals("CONFIRMADO", servicio.buscar(pedido.id()).estado());
    }

    @Test void canceladoNoConfirma() {
        var pedido = borrador(producto("PED-CANCEL"));
        var cancelado = servicio.cancelar(pedido.id(), new VersionRequest(pedido.version()));
        assertThrows(Conflicto.class, () -> servicio.confirmar(pedido.id(), new VersionRequest(cancelado.version())));
        assertEquals("CANCELADO", servicio.buscar(pedido.id()).estado());
    }

    @Test void segundaConfirmacionRechazada() {
        var pedido = borrador(producto("PED-DOBLE"));
        var confirmado = servicio.confirmar(pedido.id(), new VersionRequest(pedido.version()));
        assertThrows(Conflicto.class, () -> servicio.confirmar(pedido.id(), new VersionRequest(confirmado.version())));
    }

    @Test void segundoProductoAusenteNoGuardaPedidoParcial() {
        var producto = producto("PED-AUSENTE");
        long antes = pedidos.count();
        assertThrows(ProductoNoEncontrado.class, () -> servicio.crear(new PedidoRequest(List.of(
            new DetalleRequest(producto.id(), 2), new DetalleRequest(Long.MAX_VALUE, 1)))));
        assertEquals(antes, pedidos.count());
    }

    @Test void rollbackDeConfirmacionReviertePrimerDetalle() {
        var primero = producto("PED-ROLL-1");
        var segundo = producto("PED-ROLL-2");
        var pedido = servicio.crear(new PedidoRequest(List.of(new DetalleRequest(primero.id(), 2),
            new DetalleRequest(segundo.id(), 1))));
        jdbc.update("UPDATE producto SET activo = FALSE WHERE id = ?", segundo.id());
        assertThrows(Conflicto.class, () -> servicio.confirmar(pedido.id(), new VersionRequest(pedido.version())));
        var guardado = servicio.buscar(pedido.id());
        assertEquals("BORRADOR", guardado.estado());
        assertEquals(pedido.version(), guardado.version());
        assertTrue(guardado.detalles().stream().allMatch(d -> d.precioUnitario() == null));
        assertEquals(0, guardado.total().signum());
    }

    @Test void detalleNoBorraProductoCompartido() {
        var producto = producto("PED-CASCADE");
        var pedido = borrador(producto);
        new TransactionTemplate(tx).executeWithoutResult(t -> pedidos.deleteById(pedido.id()));
        assertEquals(producto.id(), productos.buscar(producto.id()).id());
        assertEquals(0, jdbc.queryForObject("SELECT COUNT(*) FROM detalle_pedido WHERE pedido_id = ?", Integer.class, pedido.id()));
    }

    @Test void cantidadesYListaInvalidas400() throws Exception {
        for (String cuerpo : List.of("""
            {"detalles":[]}
            """, """
            {"detalles":[null]}
            """, """
            {"detalles":[{"productoId":1,"cantidad":0}]}
            """)) {
            mvc.perform(post("/api/pedidos").with(user("editor").authorities(() -> "PRODUCT_WRITE"))
                .with(csrf()).contentType("application/json").content(cuerpo)).andExpect(status().isBadRequest());
        }
    }

    @Test void consultaPedidoExigePermiso() throws Exception {
        mvc.perform(get("/api/pedidos/1")).andExpect(status().isUnauthorized());
        mvc.perform(get("/api/pedidos/1").with(user("lector").authorities(() -> "PRODUCT_READ")))
            .andExpect(status().isForbidden());
    }

    @Test void creacionYConfirmacionHttp() throws Exception {
        var producto = producto("PED-HTTP");
        var editor = user("editor").authorities(() -> "PRODUCT_WRITE");
        String cuerpo = mapper.writeValueAsString(new PedidoRequest(
            List.of(new DetalleRequest(producto.id(), 2))));
        var respuesta = mvc.perform(post("/api/pedidos").with(editor).with(csrf())
            .contentType("application/json").content(cuerpo)).andExpect(status().isCreated())
            .andExpect(header().exists("Location")).andExpect(jsonPath("$.estado").value("BORRADOR"))
            .andReturn().getResponse().getContentAsString();
        long id = mapper.readTree(respuesta).get("id").asLong();
        mvc.perform(post("/api/pedidos/" + id + "/confirmacion").with(editor).with(csrf())
            .contentType("application/json").content(mapper.writeValueAsString(new VersionRequest(0L))))
            .andExpect(status().isOk()).andExpect(jsonPath("$.total").value(20));
        mvc.perform(post("/api/pedidos/" + id + "/confirmacion").with(editor).with(csrf())
            .contentType("application/json").content(mapper.writeValueAsString(new VersionRequest(0L))))
            .andExpect(status().isConflict());
    }
}
