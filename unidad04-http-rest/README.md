# Unidad 04 — HTTP y diseño REST

## Recurso
Diseña URLs alrededor de recursos:
```text
GET    /api/productos
GET    /api/productos/{id}
POST   /api/productos
PUT    /api/productos/{id}
DELETE /api/productos/{id}
```

## Códigos
- 200 OK;
- 201 Created;
- 204 No Content;
- 400 Bad Request;
- 404 Not Found;
- 409 Conflict;
- 500 Internal Server Error.

El código depende del resultado real, no de una plantilla fija.

## Idempotencia
Comprende la semántica de métodos antes de diseñar operaciones.

## Reto
Diseña el contrato HTTP de una API de tareas sin escribir Spring todavía.
