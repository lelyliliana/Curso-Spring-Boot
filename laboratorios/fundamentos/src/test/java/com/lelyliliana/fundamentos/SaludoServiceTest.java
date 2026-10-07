package com.lelyliliana.fundamentos;
import org.junit.jupiter.api.Test;import java.time.*;import static org.junit.jupiter.api.Assertions.*;
class SaludoServiceTest {
 private final SaludoService servicio=new SaludoService(Clock.fixed(Instant.parse("2026-01-01T00:00:00Z"),ZoneOffset.UTC),new SaludoProperties("Hola",40));
 @Test void constructorSinSpring(){assertEquals(new Saludo("Hola, Leli",LocalDate.of(2026,1,1)),servicio.responder(" Leli "));}
 @Test void nombreObligatorio(){assertThrows(IllegalArgumentException.class,()->servicio.responder(" "));}
 @Test void limite(){assertThrows(IllegalArgumentException.class,()->servicio.responder("a".repeat(41)));assertNotNull(servicio.responder("a".repeat(40)));}
}
