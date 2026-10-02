# Unidad 09 — Manejo global de errores

## Qué aprenderás
Traducir fallos de aplicación a un contrato HTTP consistente sin filtrar detalles internos.

# 1. Problema

Sin estrategia:
```text
controller A → try/catch
controller B → try/catch
controller C → try/catch
```

Duplicación e inconsistencias.

# 2. RestControllerAdvice

```java
@RestControllerAdvice
class ApiErrorHandler {
    @ExceptionHandler(ProductoNoEncontradoException.class)
    ResponseEntity<ApiError> manejar(...) {
        ...
    }
}
```

Centraliza traducción **HTTP**.

La excepción puede seguir perteneciendo al dominio/aplicación sin conocer status HTTP.

# 3. Contrato

Ejemplo:

```json
{
  "code": "PRODUCT_NOT_FOUND",
  "message": "Producto no encontrado",
  "path": "/api/productos/99"
}
```

Puedes incluir timestamp/campos/detalles según necesidad, manteniendo estabilidad.

# 4. Validación

Errores de Bean Validation suelen contener múltiples campos.

Respuesta útil:

```json
{
  "code": "VALIDATION_ERROR",
  "fields": {
    "nombre": "no debe estar vacío",
    "precio": "debe ser positivo"
  }
}
```

No dependas del orden de errores.

# 5. Conflictos

Una excepción de código duplicado puede mapearse a 409 si esa es la semántica definida.

# 6. Error inesperado

```text
500
```

Respuesta externa genérica/correlacionable.

Log interno con contexto/cause.

No envíes:
- stack trace;
- SQL;
- ruta del servidor;
- secretos.

# 7. Correlación

Un identificador de petición/error puede ayudar a conectar respuesta con logs.

No incluyas información sensible en ese identificador.

# 8. No captures demasiado

Un handler `Exception.class` final puede evitar filtrar detalles, pero no debería transformar silenciosamente todos los errores en 400.

Los errores esperados deben tener handlers específicos.

# 9. Práctica guiada

Implementa:
- validación → 400;
- no encontrado → 404;
- conflicto → 409;
- inesperado → 500.

Prueba cada contrato.

# 10. Errores frecuentes
- try/catch por controller;
- 500 para todo;
- 400 para todo;
- stack trace al cliente;
- mensajes variables imposibles de consumir;
- dominio lanzando ResponseStatusException.

# 11. Reto
Formato estable de error con tests de cuatro escenarios.

# 12. Autoevaluación
1. ¿Qué hace Advice?
2. ¿Por qué error code estable?
3. ¿Qué no se expone?
4. ¿500 inesperado?
5. ¿Por qué no 400 para toda excepción?

# 13. Checklist
- [ ] Centralizo traducción.
- [ ] Mantengo contrato.
- [ ] Protejo detalles.
- [ ] Pruebo errores.

Continúa con capas y persistencia.
