# Unidad 06: ResponseEntity, headers y códigos HTTP

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

**Proyecto de esta unidad:** [laboratorios/rest-memoria](../laboratorios/rest-memoria/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

En esta etapa no existen categoriaId, version ni credenciales. Si el enunciado menciona esos campos, resuelve primero el caso REST y completa esa comparación al llegar a JPA/integración.

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

## Caso desarrollado: Representar creación y eliminación

ResponseEntity.created establece 201 y Location con el id generado. El GET de ese recurso demuestra que se guardó; DELETE devuelve 204 sin cuerpo. Esta etapa no expone version; el campo se incorpora en JPA.

### Ejecutar y comprender

1. Prepara el [entorno de tu sistema](../docs/ENTORNO.md).
2. Sigue el [laboratorio completo](LABORATORIO.md), que identifica código, prueba y resultado.
3. Ejecuta desde la raíz:

```text
mvn -f laboratorios/rest-memoria/pom.xml "-Dtest=ProductoRestTest#contratoCRUD" test
```

4. Resuelve la [práctica](PRACTICA.md).
5. Compara después con las [soluciones razonadas](SOLUCIONES.md).

### Reto explicado

Comprueba Location y Content-Type en una creación, además del status. Al llegar a JPA, añade la comprobación de version.

El objetivo es justificar una decisión con evidencia. No necesitas memorizar todas las anotaciones del proyecto avanzado para estudiar esta unidad.

## Continuar el curso

- **Unidad anterior:** [Unidad 05: Controllers y endpoints](../unidad05-controllers/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 07: DTO y mapeo](../unidad07-dto/README.md)
