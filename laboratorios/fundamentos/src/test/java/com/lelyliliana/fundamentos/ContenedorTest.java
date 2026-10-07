package com.lelyliliana.fundamentos;
import org.junit.jupiter.api.Test;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;import java.time.Clock;import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(properties="saludo.prefijo=Bienvenida")
class ContenedorTest {
 @Autowired ApplicationContext contexto;@Autowired SaludoService servicio;@Autowired SaludoProperties props;
 @Test void beansSingleton(){assertSame(servicio,contexto.getBean(SaludoService.class));assertNotNull(contexto.getBean(Clock.class));assertFalse(contexto.containsBean("saludo"));}
 @Test void configuracionExterna(){assertEquals("Bienvenida",props.prefijo());assertTrue(servicio.responder("Leli").mensaje().startsWith("Bienvenida"));}
}
