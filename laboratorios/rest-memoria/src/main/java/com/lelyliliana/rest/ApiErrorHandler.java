package com.lelyliliana.rest;

import java.util.TreeMap;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class ApiErrorHandler extends ResponseEntityExceptionHandler {
    private static ProblemDetail problema(int status, String code, String detalle) {
        var error = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status), detalle);
        error.setProperty("code", code);
        return error;
    }

    @ExceptionHandler(NoEncontrado.class)
    ResponseEntity<ProblemDetail> ausente(NoEncontrado error) {
        return ResponseEntity.status(404).body(problema(404, "NOT_FOUND", "Producto no encontrado"));
    }

    @ExceptionHandler(Conflicto.class)
    ResponseEntity<ProblemDetail> duplicado(Conflicto error) {
        return ResponseEntity.status(409).body(problema(409, "CONFLICT", "SKU ya registrado"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<ProblemDetail> invalido(IllegalArgumentException error) {
        return ResponseEntity.badRequest().body(problema(400, "INVALID_REQUEST", "Parámetros inválidos"));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException error,
        HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var respuesta = problema(400, "VALIDATION_ERROR", "Revisa los campos de entrada");
        var campos = new TreeMap<String, String>();
        error.getBindingResult().getFieldErrors()
            .forEach(e -> campos.putIfAbsent(e.getField(), e.getDefaultMessage()));
        respuesta.setProperty("campos", campos);
        return handleExceptionInternal(error, respuesta, headers, status, request);
    }
}
