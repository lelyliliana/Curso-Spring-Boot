package com.lelyliliana.productos;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestControllerAdvice
class ApiErrorHandler {
 @ExceptionHandler(ProductoNoEncontrado.class)
 ResponseEntity<?> noEncontrado(ProductoNoEncontrado e){return ResponseEntity.status(404).body(Map.of("code","PRODUCT_NOT_FOUND","message",e.getMessage()));}
 @ExceptionHandler(MethodArgumentNotValidException.class)
 ResponseEntity<?> validacion(MethodArgumentNotValidException e){return ResponseEntity.badRequest().body(Map.of("code","VALIDATION_ERROR","message","Datos de entrada inválidos"));}
}
