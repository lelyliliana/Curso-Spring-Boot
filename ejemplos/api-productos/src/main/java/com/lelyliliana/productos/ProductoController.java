package com.lelyliliana.productos;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
@RestController
@RequestMapping("/api/productos")
class ProductoController {
 private final ProductoService service;
 ProductoController(ProductoService service){this.service=service;}
 @GetMapping Pagina<ProductoResponse> listar(@RequestParam(defaultValue="") String nombre,@RequestParam(defaultValue="0") int pagina,@RequestParam(defaultValue="20") int tamano){return service.listar(nombre,pagina,tamano);}
 @GetMapping("/{id}") ProductoResponse buscar(@PathVariable Long id){return service.buscar(id);}
 @PostMapping ResponseEntity<ProductoResponse> crear(@Valid @RequestBody ProductoRequest r){
  var p=service.crear(r);return ResponseEntity.created(URI.create("/api/productos/"+p.id())).body(p);
 }
 @PutMapping("/{id}") ProductoResponse actualizar(@PathVariable Long id,@Valid @RequestBody ProductoUpdateRequest r){return service.actualizar(id,r);}
 @DeleteMapping("/{id}") ResponseEntity<Void> eliminar(@PathVariable Long id){service.eliminar(id);return ResponseEntity.noContent().build();}
}
