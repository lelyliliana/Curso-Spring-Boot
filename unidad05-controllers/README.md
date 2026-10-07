# Unidad 05: Controllers y endpoints

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

## Qué aprenderás
Mapear HTTP a métodos Java, extraer parámetros y mantener controllers como adaptadores delgados.

# 1. Controller

```java
@RestController
@RequestMapping("/api/productos")
class ProductoController {
    private final ProductoService service;

    ProductoController(ProductoService service) {
        this.service = service;
    }
}
```

`@RestController` combina semántica de controller con serialización del retorno al cuerpo según configuración/converters.

# 2. GET colección

```java
@GetMapping
List<ProductoResponse> listar() {
    return service.listar();
}
```

# 3. Path variable

```java
@GetMapping("/{id}")
ProductoResponse buscar(@PathVariable Long id) {
    return service.buscar(id);
}
```

`id` forma parte de la identidad/ruta del recurso.

# 4. Query param

```java
@GetMapping
List<ProductoResponse> listar(
    @RequestParam(required=false) String categoria) {
    ...
}
```

Se usa para modificar la consulta/filtro.

# 5. Body

```java
@PostMapping
ProductoResponse crear(
    @RequestBody CrearProductoRequest request) {
    ...
}
```

Spring deserializa el cuerpo según Content-Type/converters.

JSON inválido y DTO válido con datos inválidos son problemas distintos.

# 6. Controller delgado

Responsabilidades:
- traducir HTTP;
- recibir entrada;
- activar validación;
- delegar;
- producir contrato HTTP.

No debería:
- ejecutar SQL;
- implementar reglas extensas;
- crear repositorios;
- mezclar detalles de infraestructura.

# 7. Serialización

Un objeto retornado puede convertirse a JSON.

Eso no significa que cualquier objeto interno deba exponerse.

DTO define la frontera.

# 8. Content-Type / Accept

`Content-Type` describe formato del cuerpo enviado.

`Accept` comunica formatos aceptables para respuesta.

Un error 415 puede indicar tipo de contenido no soportado; 406 puede relacionarse con representación no aceptable.

# 9. Práctica guiada

Implementa API en memoria:
- GET /productos;
- GET /productos/{id};
- POST /productos.

Sin JPA.

Prueba con curl/cliente HTTP y observa status/headers/body.

# 10. Errores frecuentes
- reglas de negocio en controller;
- entidad como request/response;
- confundir path/query;
- ignorar Content-Type;
- retornar null para “no encontrado”.

# 11. Reto
CRUD HTTP en memoria con controller que solo adapta/delega.

# 12. Autoevaluación
1. ¿PathVariable?
2. ¿RequestParam?
3. ¿RequestBody?
4. ¿Qué hace RestController?
5. ¿Qué no debería hacer controller?
6. ¿Content-Type vs Accept?

# 13. Checklist
- [ ] Mapeo endpoints.
- [ ] Distingo entradas HTTP.
- [ ] Delego lógica.
- [ ] Mantengo contrato separado.

Continúa con respuestas.


---

## Caso desarrollado: Enlazar rutas y parámetros con casos de uso

ProductoController conoce DTO y HTTP, y delega al servicio. @PathVariable identifica recurso; @RequestParam pagina/filtra; @RequestBody recibe JSON. La colección devuelve una envoltura propia con contenido y metadatos para evitar exponer la representación interna de Page. El endpoint no acepta una URL arbitraria de proveedor.

### Ejecutar y comprender

1. Prepara el [entorno de tu sistema](../docs/ENTORNO.md).
2. Sigue el [laboratorio completo](LABORATORIO.md), que identifica código, prueba y resultado.
3. Ejecuta desde la raíz:

```text
mvn -f ejemplos/api-productos/pom.xml "-Dtest=ProductoControllerTest#listaPaginada" test
```

4. Resuelve la [práctica](PRACTICA.md).
5. Compara después con las [soluciones razonadas](SOLUCIONES.md).

### Reto explicado

Consulta nombre=base y pagina=0,tamano=2; identifica cuántos productos entran.

El objetivo es justificar una decisión con evidencia. No necesitas memorizar todas las anotaciones del proyecto avanzado para estudiar esta unidad.

## Continuar el curso

- **Unidad anterior:** [Unidad 04: HTTP y diseño de APIs REST](../unidad04-http-rest/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 06: ResponseEntity, headers y códigos HTTP](../unidad06-respuestas-http/README.md)
