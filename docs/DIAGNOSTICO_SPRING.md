# Diagnóstico de aplicaciones Spring Boot

## Método

Reproduce una sola petición, registra método/ruta/status y X-Request-Id, identifica la capa y formula una hipótesis. Lee la primera causa útil de la excepción en el entorno local. Cambia una cosa, repite y añade una prueba cuando el problema afecta el contrato. No publiques contraseñas, tokens, cookies ni volcados con datos personales.

| Síntoma | Comprobar | Prueba o corrección |
|---|---|---|
| Maven no compila | java -version y mvn -version; JDK que usa Maven | Ambos deben usar Java 21 o superior compatible con el proyecto; consulta ENTORNO |
| Tipo de prueba no encontrado tras migrar | Paquete de import y starter de Boot 4 | Consulta MIGRACION; no mezcles imports Boot 3 y 4 |
| Bean no encontrado | Component scan, @Bean y constructor | La aplicación principal debe contener los paquetes; revisa ContenedorTest |
| Arranque falla por contraseña | APP_EDITOR_PASSWORD y APP_LECTOR_PASSWORD | Configura ambas externamente; no pongas claves en application.properties |
| Puerto ocupado | Otro proceso escucha en 8080 | Usa --server.port=8081 o detén tu otro proceso |
| 400 | JSON, Content-Type, restricciones y parámetros | Consulta campos de ProblemDetail; contrasta ProductoControllerTest |
| 401 | Credenciales presentes y correctas | GET /actuator/metrics permite observar autenticación sin escritura |
| 403 al escribir | Autoridad y token CSRF/cookie de la misma sesión | peticion.py conserva cookie y obtiene token; lector sigue sin permiso |
| 404 | Ruta, identificador, categoría o SKU del proveedor | Distingue recurso inexistente de una ruta sin handler |
| 409 | SKU duplicado o versión antigua | Vuelve a consultar y decide cómo combinar cambios; no sobrescribas ciegamente |
| 500 | Excepción interna inesperada | Contrato público genérico; diagnóstico local sin filtrar SQL al consumidor |
| Error de conexión PostgreSQL | Servidor, DB_URL, usuario, clave y permisos | localhost del contenedor es el propio contenedor; Compose utiliza db |
| Flyway falla | Historial, checksums y SQL de migración | No edites migraciones aplicadas; corrige con una nueva migración y respaldo |
| LazyInitializationException | Momento de mapear entidad y asociación | Mapea DTO dentro del servicio transaccional; no actives open-in-view como solución automática |
| Exceso de consultas | Consulta real y relaciones requeridas | EntityGraph evita cargas adicionales del catálogo; mide antes de generalizar |
| 503 en cotización | Proveedor local, timeout, status y respuesta | ProveedorClientTest simula fallos sin internet; el catálogo no depende de ese proveedor |
| Actuator sin detalles | Política de exposición | Salud pública es mínima; métricas necesitan editor |
| Tarea no corre | app.tareas-habilitadas | Está deshabilitada por defecto; configúrala explícitamente |
| Docker no encuentra el JAR | Maven verify y contexto de construcción | Construye con ejemplos/api-productos como contexto |

## Secuencia práctica

1. Ejecuta SaludoServiceTest para aislar Java/configuración.
2. Ejecuta ProductoServiceTest para aislar reglas de negocio.
3. Ejecuta ProductoControllerTest para HTTP/validación/autorización.
4. Ejecuta ProductoRepositoryTest para JPA/H2.
5. Ejecuta FlujoIntegrationTest para colaboración de capas.
6. Ejecuta scripts/verificar.py --postgres para contrato del JAR y motor real.

Una prueba de repositorio con H2 no demuestra compatibilidad PostgreSQL. Un mock de servicio no demuestra que una transacción, migración o relación esté bien definida. Escoge la prueba que pueda refutar tu hipótesis.

[Entorno](ENTORNO.md) · [Verificación](VERIFICACION.md) · [Migración](MIGRACION.md)
