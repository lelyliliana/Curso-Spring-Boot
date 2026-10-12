package com.lelyliliana.productos;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_pedido")
class DetallePedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
    @Column(nullable = false) private int cantidad;
    @Column(name = "precio_unitario", precision = 12, scale = 2)
    private BigDecimal precioUnitario;

    protected DetallePedido() {}

    DetallePedido(Pedido pedido, Producto producto, int cantidad) {
        if (pedido == null || producto == null || cantidad < 1 || cantidad > 10000)
            throw new IllegalArgumentException("Detalle inválido");
        this.pedido = pedido;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    void capturarPrecio() {
        if (!producto.isActivo()) throw new Conflicto("Producto inactivo");
        precioUnitario = producto.getPrecio();
    }

    BigDecimal subtotal() {
        return precioUnitario == null ? null : precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }

    Producto getProducto() { return producto; }
    int getCantidad() { return cantidad; }
    BigDecimal getPrecioUnitario() { return precioUnitario; }
}
