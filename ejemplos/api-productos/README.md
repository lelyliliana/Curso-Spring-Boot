# API de productos: referencia completa

Java 21 y Spring Boot 4.1.1. CRUD con actualización y versión, búsqueda paginada, categoría, validación, errores ProblemDetail, Flyway, H2/PostgreSQL, proveedor HTTP con límites, CSRF/Basic, Actuator y correlación local.

1. Sigue [ENTORNO.md](../../docs/ENTORNO.md) para Java/Maven y claves locales.
2. Ejecuta desde raíz: `mvn -f ejemplos/api-productos/pom.xml verify`.
3. Inicia `mvn -f ejemplos/api-productos/pom.xml spring-boot:run` y prueba el [contrato](../../docs/CONTRATO_API.md).
4. Para PostgreSQL/Compose sigue [OPERACION.md](../../docs/OPERACION.md).
5. Comprueba el JAR con [VERIFICACION.md](../../docs/VERIFICACION.md).

## Organización

| Código | Responsabilidad |
|---|---|
| Producto/Categoria | mapeo e invariantes |
| ProductoDto | entradas/salidas y página propia |
| ProductoController/Service/Repository | transporte, caso de uso y persistencia |
| ApiErrorHandler | errores MVC y dominio |
| SecurityConfig/CsrfController | identidad, permisos y token |
| ProveedorClient | GET externo con contrato y límites |
| RequestIdFilter/ResumenJob | correlación local y resumen opcional |
| db/migration + db/dev | evolución y carga ficticia separada |

Pruebas de servicio, MVC, JPA, integración, proveedor local y configuración están en src/test. No se necesita proveedor de Internet para testear. La configuración prod exige PostgreSQL y no carga los datos ficticios. Basic/cuentas en memoria son una elección educativa; no se presenta como identidad de producción. El ejemplo no implementa stock físico ni pagos.

[Arquitectura y límites](../../docs/ARQUITECTURA.md) · [Proyecto final](../../unidad30-proyecto-final/README.md)
