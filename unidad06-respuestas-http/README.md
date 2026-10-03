# Unidad 06 — ResponseEntity, headers y códigos HTTP

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

## Qué aprenderás
Construir respuestas explícitas y representar creación, ausencia, eliminación y conflictos con semántica HTTP.

# 1. ¿Siempre ResponseEntity?

Spring puede inferir 200 al retornar un objeto.

Usa `ResponseEntity` cuando necesitas controlar status/headers/cuerpo.

No es obligatorio envolver absolutamente todos los métodos.

# 2. Crear

```java
@PostMapping
ResponseEntity<ProductoResponse> crear(
        @Valid @RequestBody CrearProductoRequest request) {

    ProductoResponse creado = service.crear(request);
    URI location = URI.create("/api/productos/" + creado.id());

    return ResponseEntity
        .created(location)
        .body(creado);
}
```

Resultado:
```text
201 Created
Location: /api/productos/42
body: representación creada
```

# 3. Consultar

Existente:
```text
200 + body
```

Inexistente:
```text
404 + error contract
```

No devuelvas `200 null` como sustituto automático de ausencia.

# 4. Eliminar

Si se eliminó y no necesitas cuerpo:

```text
204 No Content
```

Una respuesta 204 no debe incluir cuerpo de representación.

# 5. Conflicto

Crear un código que ya existe podría ser:
```text
409 Conflict
```

si ese status representa correctamente el contrato.

No conviertas toda excepción de negocio en 400.

# 6. Headers

Además de Location:
- Cache-Control;
- ETag;
- Content-Type;
- Retry-After;
etc.

No añadas headers sin necesidad.

# 7. ResponseStatusException

Puede ser útil en ciertos bordes, pero lanzar excepciones HTTP desde capas de dominio/servicio acopla lógica a transporte.

Preferimos traducir errores de aplicación a HTTP en la frontera.

# 8. Práctica guiada

Diseña tabla:

| escenario | status | headers | body |
|---|---|---|---|
| creado | 201 | Location | recurso |
| encontrado | 200 | | recurso |
| no existe | 404 | | error |
| eliminado | 204 | | ninguno |
| conflicto | 409 | | error |

# 9. Errores frecuentes
- 200 para todo;
- body con 204;
- 500 para error esperado;
- lógica HTTP dentro del dominio;
- ResponseEntity por ritual.

# 10. Reto
Implementa tabla de escenarios y prueba status/headers.

# 11. Autoevaluación
1. ¿Cuándo ResponseEntity?
2. ¿201 + Location?
3. ¿204 tiene body?
4. ¿Ausencia como 200 null?
5. ¿409?
6. ¿Por qué no acoplar servicio a HTTP?

# 12. Checklist
- [ ] Status expresa resultado.
- [ ] Uso headers con propósito.
- [ ] Separo HTTP/aplicación.
- [ ] Pruebo contrato.

Continúa con DTO.


---

## Continuar el curso

- **Unidad anterior:** [Unidad 05 — Controllers y endpoints](../unidad05-controllers/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 07 — DTO y mapeo](../unidad07-dto/README.md)
