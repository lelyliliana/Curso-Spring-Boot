package com.lelyliliana.productos;
import java.util.TreeMap;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
@RestControllerAdvice
class ApiErrorHandler extends ResponseEntityExceptionHandler {
 static ProblemDetail problema(int status,String code,String detail){
  var p=ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status),detail);p.setProperty("code",code);return p;
 }
 @ExceptionHandler(NoEncontrado.class) ResponseEntity<ProblemDetail> ausente(NoEncontrado e){return ResponseEntity.status(404).body(problema(404,"NOT_FOUND",e.getMessage()));}
 @ExceptionHandler({Conflicto.class,ObjectOptimisticLockingFailureException.class,DataIntegrityViolationException.class})
 ResponseEntity<ProblemDetail> conflicto(Exception e){return ResponseEntity.status(409).body(problema(409,"CONFLICT","El estado actual impide la operación"));}
 @ExceptionHandler(IllegalArgumentException.class) ResponseEntity<ProblemDetail> invalido(Exception e){return ResponseEntity.badRequest().body(problema(400,"INVALID_REQUEST","Datos de entrada inválidos"));}
 @ExceptionHandler(ProveedorNoDisponible.class) ResponseEntity<ProblemDetail> proveedor(Exception e){return ResponseEntity.status(503).body(problema(503,"PROVIDER_UNAVAILABLE","Proveedor temporalmente no disponible"));}
 @ExceptionHandler(Exception.class) ResponseEntity<ProblemDetail> inesperado(Exception e){
  org.slf4j.LoggerFactory.getLogger(ApiErrorHandler.class).error("Fallo inesperado: tipo={}",e.getClass().getSimpleName());
  return ResponseEntity.status(500).body(problema(500,"INTERNAL_ERROR","No fue posible completar la operación"));
 }
 @Override protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,HttpHeaders headers,HttpStatusCode status,WebRequest request){
  var p=problema(400,"VALIDATION_ERROR","Revisa los campos de entrada");
  var campos=new TreeMap<String,String>();ex.getBindingResult().getFieldErrors().forEach(e->campos.putIfAbsent(e.getField(),e.getDefaultMessage()));p.setProperty("campos",campos);
  return handleExceptionInternal(ex,p,headers,status,request);
 }
}
