package com.lelyliliana.productos;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import org.springframework.data.domain.Sort;
@RestController
@RequestMapping("/api/categorias")
class CategoriaController {
 private final CategoriaRepository repo;
 CategoriaController(CategoriaRepository repo){this.repo=repo;}
 @GetMapping List<CategoriaResponse> listar(){return repo.findAll(Sort.by("id")).stream().map(c->new CategoriaResponse(c.getId(),c.getNombre())).toList();}
 @PostMapping ResponseEntity<CategoriaResponse> crear(@Valid @RequestBody CategoriaRequest r){
  var c=repo.saveAndFlush(new Categoria(r.nombre().strip()));
  return ResponseEntity.created(URI.create("/api/categorias")).body(new CategoriaResponse(c.getId(),c.getNombre()));
 }
}
