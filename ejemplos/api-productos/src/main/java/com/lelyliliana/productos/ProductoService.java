package com.lelyliliana.productos;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;
@Service
@Transactional(readOnly=true)
class ProductoService {
 private final ProductoRepository repo;
 private final CategoriaRepository categorias;
 ProductoService(ProductoRepository repo,CategoriaRepository categorias){this.repo=repo;this.categorias=categorias;}
 Pagina<ProductoResponse> listar(String nombre,int pagina,int tamano){
  if(pagina<0 || tamano<1 || tamano>100 || nombre.length()>100)throw new IllegalArgumentException("Paginación o filtro inválido");
  var p=repo.findByNombreContainingIgnoreCase(nombre,PageRequest.of(pagina,tamano,Sort.by("id")));
  return new Pagina<>(p.map(ProductoResponse::de).getContent(),p.getNumber(),p.getSize(),p.getTotalElements(),p.getTotalPages());
 }
 ProductoResponse buscar(Long id){return ProductoResponse.de(producto(id));}
 @Transactional ProductoResponse crear(ProductoRequest r){
  if(repo.existsBySku(r.sku()))throw new Conflicto("SKU ya registrado");
  var p=new Producto(r.sku(),r.nombre(),r.precio(),categoria(r.categoriaId()));
  return ProductoResponse.de(repo.saveAndFlush(p));
 }
 @Transactional ProductoResponse actualizar(Long id,ProductoUpdateRequest r){
  var p=producto(id);
  if(!p.getVersion().equals(r.version()))throw new Conflicto("Versión desactualizada");
  if(repo.existsBySkuAndIdNot(r.sku(),id))throw new Conflicto("SKU ya registrado");
  p.actualizar(r.sku(),r.nombre(),r.precio(),categoria(r.categoriaId()));
  repo.flush();
  return ProductoResponse.de(p);
 }
 @Transactional void eliminar(Long id){repo.delete(producto(id));repo.flush();}
 private Producto producto(Long id){return repo.findById(id).orElseThrow(()->new ProductoNoEncontrado(id));}
 private Categoria categoria(Long id){return categorias.findById(id).orElseThrow(()->new NoEncontrado("Categoría no encontrada"));}
}
class NoEncontrado extends RuntimeException {NoEncontrado(String message){super(message);}}
class ProductoNoEncontrado extends NoEncontrado {ProductoNoEncontrado(Long id){super("Producto no encontrado");}}
class Conflicto extends RuntimeException {Conflicto(String message){super(message);}}
class ProveedorNoDisponible extends RuntimeException {ProveedorNoDisponible(){super("Proveedor temporalmente no disponible");}}
