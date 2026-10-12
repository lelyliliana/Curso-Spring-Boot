package com.lelyliliana.productos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

record DetalleRequest(@NotNull @Positive Long productoId, @Min(1) @Max(10000) int cantidad) {}
record PedidoRequest(@NotNull @Size(min = 1, max = 50) List<@NotNull @Valid DetalleRequest> detalles) {}
record VersionRequest(@NotNull @PositiveOrZero Long version) {}
record DetalleResponse(Long productoId, int cantidad, BigDecimal precioUnitario, BigDecimal subtotal) {}
record PedidoResponse(Long id, Long version, String estado, Instant fecha, String moneda,
    BigDecimal total, List<DetalleResponse> detalles) {
    static PedidoResponse de(Pedido pedido) {
        return new PedidoResponse(pedido.getId(), pedido.getVersion(), pedido.getEstado().name(),
            pedido.getFecha(), "COP", pedido.getTotal(), pedido.getDetalles().stream()
                .map(d -> new DetalleResponse(d.getProducto().getId(), d.getCantidad(),
                    d.getPrecioUnitario(), d.subtotal())).toList());
    }
}
