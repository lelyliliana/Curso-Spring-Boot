package com.lelyliliana.fundamentos;
import org.springframework.web.bind.annotation.*;import org.springframework.http.ProblemDetail;
@RestController
class SaludoController {
 private final SaludoService service;
 SaludoController(SaludoService service){this.service=service;}
 @GetMapping("/api/saludos") Saludo saludo(@RequestParam(defaultValue="estudiante") String nombre){return service.responder(nombre);}
 @ExceptionHandler(IllegalArgumentException.class) ProblemDetail invalido(){return ProblemDetail.forStatusAndDetail(org.springframework.http.HttpStatus.BAD_REQUEST,"Nombre inválido");}
}
