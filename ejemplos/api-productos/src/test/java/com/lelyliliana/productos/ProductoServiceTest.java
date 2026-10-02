package com.lelyliliana.productos;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
class ProductoServiceTest {
 @Test void buscaProducto(){
  var repo=mock(ProductoRepository.class);
  when(repo.findById(1L)).thenReturn(Optional.of(new Producto("Teclado",new BigDecimal("100.00"))));
  var service=new ProductoService(repo);
  assertEquals("Teclado",service.buscar(1L).nombre());
 }
 @Test void inexistenteLanzaExcepcion(){
  var repo=mock(ProductoRepository.class);
  when(repo.findById(99L)).thenReturn(Optional.empty());
  assertThrows(ProductoNoEncontrado.class,()->new ProductoService(repo).buscar(99L));
 }
}
