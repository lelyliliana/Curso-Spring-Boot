# Unidad 09 — Manejo global de errores

## Problema
No queremos try/catch repetido en cada controller.

## @RestControllerAdvice
Centraliza traducción de excepciones a respuestas HTTP.

Ejemplo de error:
```json
{
  "code": "PRODUCT_NOT_FOUND",
  "message": "Producto no encontrado",
  "path": "/api/productos/99"
}
```

## No filtres detalles internos
Stack traces, SQL o secretos no deben formar parte de respuestas públicas.

## Consistencia
Define un formato de error estable.

## Reto
Diseña respuestas para validación, recurso inexistente, conflicto y error inesperado.
