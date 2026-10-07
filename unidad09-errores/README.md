# Unidad 09: Manejo global de errores

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

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


---

## Caso desarrollado: Representar errores sin divulgar información interna

ApiErrorHandler usa ProblemDetail para 400/404/409/503 y fallos inesperados 500. ResponseEntityExceptionHandler conserva manejo de errores del protocolo como JSON ilegible, tipo incorrecto, método no permitido o formato no soportado. No se retorna e.getMessage de una excepción SQL. Los errores de la cadena de seguridad requieren handlers propios porque ocurren antes de MVC.

### Ejecutar y comprender

1. Prepara el [entorno de tu sistema](../docs/ENTORNO.md).
2. Sigue el [laboratorio completo](LABORATORIO.md), que identifica código, prueba y resultado.
3. Ejecuta desde la raíz:

```text
mvn -f ejemplos/api-productos/pom.xml "-Dtest=ProductoControllerTest#jsonMalformado400" test
```

4. Resuelve la [práctica](PRACTICA.md).
5. Compara después con las [soluciones razonadas](SOLUCIONES.md).

### Reto explicado

Escribe qué debe conocer un cliente de un conflicto sin recibir SQL o stack trace.

El objetivo es justificar una decisión con evidencia. No necesitas memorizar todas las anotaciones del proyecto avanzado para estudiar esta unidad.

## Continuar el curso

- **Unidad anterior:** [Unidad 08: Validación de entrada y reglas de negocio](../unidad08-validacion/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 10: Capas y responsabilidades](../unidad10-capas/README.md)
