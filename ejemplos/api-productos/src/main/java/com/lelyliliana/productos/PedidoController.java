package com.lelyliliana.productos;

import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
class PedidoController {
    private final PedidoService servicio;

    PedidoController(PedidoService servicio) { this.servicio = servicio; }

    @PostMapping
    ResponseEntity<PedidoResponse> crear(@Valid @RequestBody PedidoRequest entrada) {
        var pedido = servicio.crear(entrada);
        return ResponseEntity.created(URI.create("/api/pedidos/" + pedido.id())).body(pedido);
    }

    @GetMapping("/{id}")
    PedidoResponse buscar(@PathVariable Long id) { return servicio.buscar(id); }

    @PostMapping("/{id}/confirmacion")
    PedidoResponse confirmar(@PathVariable Long id, @Valid @RequestBody VersionRequest entrada) {
        return servicio.confirmar(id, entrada);
    }

    @PostMapping("/{id}/cancelacion")
    PedidoResponse cancelar(@PathVariable Long id, @Valid @RequestBody VersionRequest entrada) {
        return servicio.cancelar(id, entrada);
    }
}
