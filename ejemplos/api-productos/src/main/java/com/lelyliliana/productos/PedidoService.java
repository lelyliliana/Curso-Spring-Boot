package com.lelyliliana.productos;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
class PedidoService {
    private final PedidoRepository pedidos;
    private final ProductoRepository productos;

    PedidoService(PedidoRepository pedidos, ProductoRepository productos) {
        this.pedidos = pedidos;
        this.productos = productos;
    }

    @Transactional
    PedidoResponse crear(PedidoRequest entrada) {
        if (entrada.detalles() == null || entrada.detalles().isEmpty() || entrada.detalles().size() > 50)
            throw new IllegalArgumentException("Detalles inválidos");
        var pedido = new Pedido();
        for (var detalle : entrada.detalles()) {
            if (detalle == null) throw new IllegalArgumentException("Detalle nulo");
            var producto = productos.findById(detalle.productoId())
                .orElseThrow(() -> new ProductoNoEncontrado(detalle.productoId()));
            pedido.agregar(producto, detalle.cantidad());
        }
        return PedidoResponse.de(pedidos.saveAndFlush(pedido));
    }

    PedidoResponse buscar(Long id) { return PedidoResponse.de(pedido(id)); }

    @Transactional
    PedidoResponse confirmar(Long id, VersionRequest entrada) {
        var pedido = pedido(id);
        comprobarVersion(pedido, entrada);
        pedido.confirmar();
        pedidos.flush();
        return PedidoResponse.de(pedido);
    }

    @Transactional
    PedidoResponse cancelar(Long id, VersionRequest entrada) {
        var pedido = pedido(id);
        comprobarVersion(pedido, entrada);
        pedido.cancelar();
        pedidos.flush();
        return PedidoResponse.de(pedido);
    }

    private Pedido pedido(Long id) {
        return pedidos.findById(id).orElseThrow(() -> new NoEncontrado("Pedido no encontrado"));
    }

    private void comprobarVersion(Pedido pedido, VersionRequest entrada) {
        if (entrada.version() == null || entrada.version() < 0)
            throw new IllegalArgumentException("Versión inválida");
        if (!pedido.getVersion().equals(entrada.version())) throw new Conflicto("Versión desactualizada");
    }
}
