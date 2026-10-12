package com.lelyliliana.rest;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductoRestTest {
    @Autowired MockMvc mvc;
    @Autowired JsonMapper mapper;

    @Test void contratoCRUD() throws Exception {
        var cuerpo = mvc.perform(post("/api/productos").contentType("application/json")
            .content("{"sku":"CRUD","nombre":"Libro","precio":10}"))
            .andExpect(status().isCreated()).andExpect(header().exists("Location"))
            .andExpect(content().contentTypeCompatibleWith("application/json"))
            .andReturn().getResponse().getContentAsString();
        long id = mapper.readTree(cuerpo).get("id").asLong();
        mvc.perform(get("/api/productos/" + id)).andExpect(jsonPath("$.nombre").value("Libro"));
        mvc.perform(put("/api/productos/" + id).contentType("application/json")
            .content("{"sku":"CRUD","nombre":"Nuevo","precio":15}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.precio").value(15));
        mvc.perform(delete("/api/productos/" + id)).andExpect(status().isNoContent())
            .andExpect(content().string(""));
        mvc.perform(get("/api/productos/" + id)).andExpect(status().isNotFound());
    }

    @Test void validaDatos() throws Exception {
        mvc.perform(post("/api/productos").contentType("application/json")
            .content("{"sku":"bad","nombre":"","precio":1.001}"))
            .andExpect(status().isBadRequest()).andExpect(jsonPath("$.campos.precio").exists());
    }

    @Test void skuUnico() {
        var servicio = new ProductoService();
        var entrada = new ProductoRequest("LIB-1", "Libro", new BigDecimal("10"));
        servicio.crear(entrada);
        assertThrows(Conflicto.class, () -> servicio.crear(entrada));
        assertEquals(1, servicio.listar("", 0, 20).total());
    }

    @Test void paginacionYFiltro() {
        var servicio = new ProductoService();
        servicio.crear(new ProductoRequest("LIB-1", "Bases de datos", new BigDecimal("10")));
        servicio.crear(new ProductoRequest("LIB-2", "Algoritmos", new BigDecimal("20")));
        var pagina = servicio.listar("BASE", 0, 2);
        assertEquals(1, pagina.total());
        assertEquals("Bases de datos", pagina.contenido().getFirst().nombre());
        assertTrue(servicio.listar("", Integer.MAX_VALUE, 100).contenido().isEmpty());
    }

    @Test void paginaInvalida() throws Exception {
        mvc.perform(get("/api/productos?tamano=101")).andExpect(status().isBadRequest());
    }

    @Test void erroresNoFiltranInternos() throws Exception {
        mvc.perform(get("/api/productos/999999")).andExpect(status().isNotFound())
            .andExpect(content().contentTypeCompatibleWith("application/problem+json"))
            .andExpect(jsonPath("$.code").value("NOT_FOUND"))
            .andExpect(jsonPath("$.trace").doesNotExist());
    }

    @Test void actualizarRechazaSkuAjeno() {
        var servicio = new ProductoService();
        var primero = servicio.crear(new ProductoRequest("A", "Uno", BigDecimal.ONE));
        servicio.crear(new ProductoRequest("B", "Dos", BigDecimal.TEN));
        assertThrows(Conflicto.class, () -> servicio.actualizar(primero.id(),
            new ProductoRequest("B", "Cambio", BigDecimal.TEN)));
        assertEquals("Uno", servicio.buscar(primero.id()).nombre());
    }
}
