package com.lelyliliana.productos;
import org.junit.jupiter.api.Test;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.flyway.autoconfigure.FlywayAutoConfiguration;
import org.springframework.data.domain.*;
import org.springframework.dao.DataIntegrityViolationException;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
@DataJpaTest
@ImportAutoConfiguration(FlywayAutoConfiguration.class)
class ProductoRepositoryTest {
 @Autowired ProductoRepository repo;@Autowired CategoriaRepository categorias;
 @Test void filtroYPaginaEstable(){var p=repo.findByNombreContainingIgnoreCase("a",PageRequest.of(0,2,Sort.by("id")));assertEquals(2,p.getContent().size());assertEquals(3,p.getTotalElements());assertEquals("Algoritmos",p.getContent().getFirst().getNombre());assertEquals("Libros",p.getContent().getFirst().getCategoria().getNombre());}
 @Test void duplicadoReal(){assertThrows(DataIntegrityViolationException.class,()->repo.saveAndFlush(new Producto("LIB-01","Otro",new BigDecimal("10"),categorias.findById(1L).orElseThrow())));}
 @Test void categoriaConHijosProtegida(){assertThrows(DataIntegrityViolationException.class,()->{categorias.deleteById(1L);categorias.flush();});}
 @Test void relacionPersistida(){var p=repo.saveAndFlush(new Producto("TEST-01","Prueba",new BigDecimal("12.50"),categorias.findById(2L).orElseThrow()));assertNotNull(p.getId());assertEquals(0L,p.getVersion());assertEquals("Tecnología",p.getCategoria().getNombre());}
}
