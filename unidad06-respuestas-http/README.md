# Unidad 06 — Request, ResponseEntity y códigos HTTP

## Crear
```java
@PostMapping
ResponseEntity<ProductoResponse> crear(@RequestBody CrearProductoRequest request) {
    ProductoResponse creado = service.crear(request);
    URI location = URI.create("/api/productos/" + creado.id());
    return ResponseEntity.created(location).body(creado);
}
```

## Eliminar
Una operación exitosa sin cuerpo puede responder 204.

## Headers
Location comunica la URI del recurso creado.

## No devuelvas 200 para todo
El contrato HTTP debe expresar resultado.

## Reto
Define respuestas para crear, consultar inexistente, actualizar conflicto y eliminar.
