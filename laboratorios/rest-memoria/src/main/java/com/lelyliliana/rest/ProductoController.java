package com.lelyliliana.rest;

import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
class ProductoController {
    private final ProductoService servicio;

    ProductoController(ProductoService servicio) { this.servicio = servicio; }

    @GetMapping
    Pagina<ProductoResponse> listar(@RequestParam(defaultValue = "") String nombre,
        @RequestParam(defaultValue = "0") int pagina, @RequestParam(defaultValue = "20") int tamano) {
        return servicio.listar(nombre, pagina, tamano);
    }

    @GetMapping("/{id}")
    ProductoResponse buscar(@PathVariable Long id) { return servicio.buscar(id); }

    @PostMapping
    ResponseEntity<ProductoResponse> crear(@Valid @RequestBody ProductoRequest entrada) {
        var creado = servicio.crear(entrada);
        return ResponseEntity.created(URI.create("/api/productos/" + creado.id())).body(creado);
    }

    @PutMapping("/{id}")
    ProductoResponse actualizar(@PathVariable Long id, @Valid @RequestBody ProductoRequest entrada) {
        return servicio.actualizar(id, entrada);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
