# Unidad 05: Controllers y endpoints

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

**Proyecto de esta unidad:** [laboratorios/rest-memoria](../laboratorios/rest-memoria/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

En esta etapa no existen categoriaId, version ni credenciales. Si el enunciado menciona esos campos, resuelve primero el caso REST y completa esa comparación al llegar a JPA/integración.

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

## Caso desarrollado: Leer parámetros y filtrar resultados

ProductoController recibe nombre, pagina y tamano. El servicio filtra sin distinguir mayúsculas, ordena por id y aplica límite. El catálogo inicia vacío: crea primero Bases de datos y Algoritmos con SKU distintos para poder predecir el resultado.

### Ejecutar y comprender

1. Prepara el [entorno de tu sistema](../docs/ENTORNO.md).
2. Sigue el [laboratorio completo](LABORATORIO.md), que identifica código, prueba y resultado.
3. Ejecuta desde la raíz:

```text
mvn -f laboratorios/rest-memoria/pom.xml "-Dtest=ProductoRestTest#paginacionYFiltro" test
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

---

### ¿Necesitas apoyo con este tema?

Si te bloqueaste con la práctica, necesitas revisar tu entorno o adaptar lo aprendido a tu proyecto, podemos trabajarlo en una sesión personalizada.

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2005%20%E2%80%94%20Controllers%20y%20endpoints%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad05-controllers%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
