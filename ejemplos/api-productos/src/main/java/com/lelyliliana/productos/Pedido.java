package com.lelyliliana.productos;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
class Pedido {
    enum Estado { BORRADOR, CONFIRMADO, CANCELADO }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version private Long version;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 16)
    private Estado estado = Estado.BORRADOR;
    @Column(nullable = false) private Instant fecha = Instant.now();
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal total = new BigDecimal("0.00");

    // El pedido posee sus detalles; los productos del catálogo son compartidos.
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<DetallePedido> detalles = new ArrayList<>();

    protected Pedido() {}

    void agregar(Producto producto, int cantidad) {
        if (estado != Estado.BORRADOR || detalles.size() >= 50 ||
            detalles.stream().anyMatch(d -> d.getProducto().getId().equals(producto.getId())))
            throw new IllegalArgumentException("Detalles inválidos");
        detalles.add(new DetallePedido(this, producto, cantidad));
    }

    void confirmar() {
        if (estado != Estado.BORRADOR) throw new Conflicto("Pedido no confirmable");
        if (detalles.isEmpty()) throw new IllegalArgumentException("Pedido vacío");
        // Si falla un detalle, la transacción del servicio revierte también los anteriores.
        for (var detalle : detalles) detalle.capturarPrecio();
        total = detalles.stream().map(DetallePedido::subtotal)
            .reduce(new BigDecimal("0.00"), BigDecimal::add);
        estado = Estado.CONFIRMADO;
    }

    void cancelar() {
        if (estado == Estado.CANCELADO) throw new Conflicto("Pedido ya cancelado");
        estado = Estado.CANCELADO;
        // Se conserva el total/precio histórico si el pedido ya estaba confirmado.
    }

    Long getId() { return id; }
    Long getVersion() { return version; }
    Estado getEstado() { return estado; }
    Instant getFecha() { return fecha; }
    BigDecimal getTotal() { return total; }
    List<DetallePedido> getDetalles() { return List.copyOf(detalles); }
}
