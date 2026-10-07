package com.lelyliliana.productos;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;import java.util.Optional;
import org.junit.jupiter.api.Test;
class ProductoServiceTest {
 private final ProductoRepository repo=mock(ProductoRepository.class);
 private final CategoriaRepository categorias=mock(CategoriaRepository.class);
 private final ProductoService service=new ProductoService(repo,categorias);
 @Test void buscaProducto(){when(repo.findById(1L)).thenReturn(Optional.of(new Producto("TEC-1","Teclado",new BigDecimal("100.00"),new Categoria("Tecnología"))));assertEquals("Teclado",service.buscar(1L).nombre());}
 @Test void inexistente(){when(repo.findById(99L)).thenReturn(Optional.empty());assertThrows(ProductoNoEncontrado.class,()->service.buscar(99L));}
 @Test void duplicadoNoPersiste(){when(repo.existsBySku("DUP")).thenReturn(true);assertThrows(Conflicto.class,()->service.crear(new ProductoRequest("DUP","Libro",new BigDecimal("10"),1L)));verify(repo,never()).saveAndFlush(any());}
 @Test void categoriaAusenteNoPersiste(){when(categorias.findById(99L)).thenReturn(Optional.empty());assertThrows(NoEncontrado.class,()->service.crear(new ProductoRequest("NUEVO","Libro",new BigDecimal("10"),99L)));verify(repo,never()).saveAndFlush(any());}
 @Test void paginaAcotada(){assertThrows(IllegalArgumentException.class,()->service.listar("",-1,20));assertThrows(IllegalArgumentException.class,()->service.listar("",0,101));verifyNoInteractions(repo);}
 @Test void entidadValidaFueraDeSpring(){var p=new Producto("LIB-1","  Libro  ",new BigDecimal("25.50"),new Categoria("Libros"));assertEquals("Libro",p.getNombre());assertEquals(new BigDecimal("25.50"),p.getPrecio());}
 @org.junit.jupiter.params.ParameterizedTest
 @org.junit.jupiter.params.provider.ValueSource(strings={"0","-1","1.001","10000000000"})
 void precioInvalido(String precio){assertThrows(IllegalArgumentException.class,()->new Producto("LIB-1","Libro",new BigDecimal(precio),new Categoria("Libros")));}
 @org.junit.jupiter.params.ParameterizedTest
 @org.junit.jupiter.params.provider.ValueSource(strings={"abc","CON ESPACIO","","<SCRIPT>"})
 void skuInvalido(String sku){assertThrows(IllegalArgumentException.class,()->new Producto(sku,"Libro",new BigDecimal("1"),new Categoria("Libros")));}
}
