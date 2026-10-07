# Contrato de la API de productos

Datos ficticios, importes decimales en unidades monetarias del ejemplo. La API no implementa facturación, impuestos ni stock físico.

| Método/ruta | Entrada | Éxito | Fallos relevantes |
|---|---|---|---|
| GET /api/productos | nombre (texto), pagina≥0, tamano 1..100 | 200 Pagina | 400 parámetros |
| GET /api/productos/{id} | id | 200 ProductoResponse | 404 |
| POST /api/productos | sku,nombre,precio,categoriaId | 201 + Location y ProductoResponse | 400/404/409; acceso 401/403 |
| PUT /api/productos/{id} | anteriores + version≥0 | 200 ProductoResponse | 400/404/409; acceso 401/403 |
| DELETE /api/productos/{id} | id | 204 sin cuerpo | 404; acceso 401/403 |
| GET /api/categorias | sin entrada | 200 lista | |
| POST /api/categorias | nombre | 201 CategoriaResponse | 400/409; acceso 401/403 |
| GET /api/cotizaciones/{sku} | SKU restringido | 200 sku/precio | 400/404/503 |
| GET /api/csrf | conserva cookie de sesión | 200 header/token | |
| GET /actuator/health | público | 200 UP cuando saludable | puede 503 si DOWN |
| GET /actuator/metrics | authority OPS_READ | 200 | 401/403 |

## Reglas de datos

SKU: 1..32 caracteres A–Z, 0–9 y guion. Nombre: no vacío y hasta 100 caracteres; se recortan espacios de extremos. Precio: 0.01 mínimo, hasta diez dígitos enteros y dos decimales. Categoría: id positivo y existente. El servidor genera id/version inicial; el cliente debe utilizar los valores de la respuesta. PUT requiere versión leída; un conflicto no se resuelve sobreescribiendo sin revisar el estado nuevo.

Pagina contiene contenido,pagina,tamano,total,paginas. Orden estable por id. La carga dev inicial contiene cuatro productos (Algoritmos 80, Bases de datos 90, Teclado 120 y Mouse 90) y categorías Libros, Tecnología y Hogar. No hay datos dev en perfil prod.

## Seguridad de escritura

El cliente debe obtener /api/csrf, mantener cookie y enviar el header indicado en POST/PUT/DELETE junto con Basic de editor. Sin CSRF puede recibir 403 aun antes de autenticarse. Con CSRF válido y sin identidad recibe 401. Con lector válido y CSRF recibe 403 por authority. Las identidades son editor (PRODUCT_WRITE,OPS_READ) y lector (PRODUCT_READ); sus claves se configuran fuera de Git. Basic requiere TLS fuera de HTTP local. No hay CORS cross-origin configurado; este cliente es terminal, no un navegador externo.

## Errores

ProblemDetail conserva status, title/detail y campos específicos cuando corresponde. Código estable para errores de dominio: NOT_FOUND, CONFLICT, INVALID_REQUEST, VALIDATION_ERROR, PROVIDER_UNAVAILABLE, INTERNAL_ERROR; seguridad: UNAUTHORIZED/FORBIDDEN. Los errores estándar de protocolo manejados por ResponseEntityExceptionHandler pueden no incluir code personalizado. No supongas que todo 400 tiene idéntica forma de campos.

No se devuelve SQL, stacktrace ni cuerpo bruto del proveedor. X-Request-Id permite correlación local; no identifica al usuario ni representa una traza distribuida completa.

## Proveedor

GET consulta y no actualiza el precio persistido. 404 se traduce a recurso no encontrado; rate limit, respuestas transitorias e inválidas a 503. Por defecto no se reintenta. La configuración permite como máximo dos intentos para 502/503/504 de este GET; no aplica a pagos ni a escrituras. No se implementa breaker ni presupuesto global.

[Cliente y entorno](ENTORNO.md) · [Arquitectura](ARQUITECTURA.md)
