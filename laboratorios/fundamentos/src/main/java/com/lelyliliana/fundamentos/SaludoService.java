package com.lelyliliana.fundamentos;
import org.springframework.stereotype.Service;import java.time.*;
@Service
class SaludoService {
 private final Clock clock;private final SaludoProperties propiedades;
 SaludoService(Clock clock,SaludoProperties propiedades){this.clock=clock;this.propiedades=propiedades;}
 Saludo responder(String nombre){
  if(nombre==null || nombre.isBlank() || nombre.strip().length()>propiedades.longitudMaxima())throw new IllegalArgumentException("Nombre inválido");
  return new Saludo(propiedades.prefijo()+", "+nombre.strip(),LocalDate.now(clock));
 }
}
record Saludo(String mensaje,LocalDate fecha){}
