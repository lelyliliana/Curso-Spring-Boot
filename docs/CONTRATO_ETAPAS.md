# Contratos de las etapas REST y JPA

Estos proyectos son laboratorios locales sin Spring Security. El cliente Python usa --sin-csrf en sus escrituras porque estos servidores no tienen /api/csrf. En la API integrada conserva CSRF y --usuario editor.

## REST en memoria (unidades 04–10)

| Método/ruta | Entrada | Éxito | Rechazos |
|---|---|---|---|
| GET /api/productos | nombre, pagina, tamano | 200, página propia | 400 parámetros inválidos |
| GET /api/productos/{id} | id numérico | 200, producto | 404 inexistente |
| POST /api/productos | sku, nombre, precio | 201, Location y producto | 400 validación; 409 SKU duplicado |
| PUT /api/productos/{id} | sku, nombre, precio | 200, producto actualizado | 400; 404; 409 SKU de otro producto |
| DELETE /api/productos/{id} | id | 204, sin cuerpo | 404 |

Ejemplo de entrada (datos/producto-memoria.json):

```json
{"sku":"LAB-01","nombre":"Libro nuevo","precio":25.50}
```

Respuesta de creación en una ejecución recién iniciada:

```json
{"id":1,"sku":"LAB-01","nombre":"Libro nuevo","precio":25.50}
```

La página usa contenido, pagina, tamano, total y paginas. El SKU acepta A–Z, números y guion, de 1–32 caracteres; nombre no vacío hasta 100; precio positivo de hasta dos decimales. La página comienza en 0 y tamano acepta 1–100. No existen categoriaId ni version en esta etapa. El catálogo comienza vacío y se pierde al detener el proceso.

## JPA (unidades 11–14)

Mantiene las rutas y estados del catálogo, pero POST exige categoriaId existente y PUT exige además la version actual. La respuesta añade categoriaId, categoria, version y activo. Categoría ausente: 404. Versión antigua o restricción UNIQUE/FK: 409.

El perfil dev contiene cuatro productos y tres categorías. GET /api/categorias devuelve categorías y POST /api/categorias permite crear con nombre no vacío hasta 80 caracteres. En prod la base nueva está vacía: usa los ids devueltos al crear.

Ejemplo de POST:

```json
{"sku":"LAB-01","nombre":"Libro nuevo","precio":25.50,"categoriaId":1}
```

Ejemplo de PUT para un producto creado con version 0:

```json
{"sku":"LAB-01","nombre":"Libro actualizado","precio":30.00,"categoriaId":1,"version":0}
```

La actualización devuelve version 1; repetir 0 devuelve 409 y no cambia los datos. Lee y conserva la versión de tu respuesta, porque puede ser mayor en otras ejecuciones.

## Respuestas de error

Los errores de dominio/validación usan application/problem+json, status y code. La validación también puede incluir campos. Los errores estándar de protocolo (por ejemplo, JSON ilegible o método no permitido) pueden tener ProblemDetail sin code personalizado.

[Ruta](RUTA_PROGRESIVA.md) · [REST](../laboratorios/rest-memoria/README.md) · [JPA](../laboratorios/jpa-productos/README.md) · [Contrato integrado](CONTRATO_API.md)
