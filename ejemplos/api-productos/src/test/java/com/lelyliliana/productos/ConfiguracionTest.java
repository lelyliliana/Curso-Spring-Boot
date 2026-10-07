package com.lelyliliana.productos;
import org.junit.jupiter.api.Test;import jakarta.validation.Validation;import java.net.URI;import java.time.Duration;import static org.junit.jupiter.api.Assertions.*;
class ConfiguracionTest {
 @Test void configuracionValida(){try(var f=Validation.buildDefaultValidatorFactory()){assertTrue(f.getValidator().validate(new AppProperties("API","prueba-editor","prueba-lector",URI.create("http://localhost:9090"),Duration.ofMillis(50),Duration.ofMillis(50),1)).isEmpty());}}
 @Test void configuracionInvalida(){try(var f=Validation.buildDefaultValidatorFactory()){assertFalse(f.getValidator().validate(new AppProperties("","","",URI.create("file:///etc/hosts"),Duration.ZERO,Duration.ofSeconds(20),9)).isEmpty());}}
 @Test void jobPuedeProbarseSinEsperarReloj(){var repo=org.mockito.Mockito.mock(ProductoRepository.class);org.mockito.Mockito.when(repo.count()).thenReturn(4L);new ResumenJob(repo).ejecutar();org.mockito.Mockito.verify(repo).count();}
}
