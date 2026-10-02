package com.lelyliliana.productos;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
class ProductoService {
 private final ProductoRepository repo;
 ProductoService(ProductoRepository repo){this.repo=repo;}
 List<ProductoResponse> listar(){return repo.findAll().stream().map(this::dto).toList();}
 ProductoResponse buscar(Long id){return dto(repo.findById(id).orElseThrow(()->new ProductoNoEncontrado(id)));}
 ProductoResponse crear(ProductoRequest r){return dto(repo.save(new Producto(r.nombre(),r.precio())));}
 void eliminar(Long id){if(!repo.existsById(id))throw new ProductoNoEncontrado(id);repo.deleteById(id);}
 private ProductoResponse dto(Producto p){return new ProductoResponse(p.getId(),p.getNombre(),p.getPrecio());}
}
class ProductoNoEncontrado extends RuntimeException{ProductoNoEncontrado(Long id){super("Producto "+id+" no encontrado");}}
