package com.lelyliliana.productos;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
@RestController
@RequestMapping("/api/productos")
class ProductoController {
 private final ProductoService service;
 ProductoController(ProductoService service){this.service=service;}
 @GetMapping List<ProductoResponse> listar(){return service.listar();}
 @GetMapping("/{id}") ProductoResponse buscar(@PathVariable Long id){return service.buscar(id);}
 @PostMapping ResponseEntity<ProductoResponse> crear(@Valid @RequestBody ProductoRequest r){
  var creado=service.crear(r);
  return ResponseEntity.created(URI.create("/api/productos/"+creado.id())).body(creado);
 }
 @DeleteMapping("/{id}") ResponseEntity<Void> eliminar(@PathVariable Long id){service.eliminar(id);return ResponseEntity.noContent().build();}
}
