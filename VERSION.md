# Versión 2.2

Fecha: 2026-10-14 (America/Bogota).

Las 31 unidades incorporan DESARROLLO.md con lectura del código, decisiones, contrastes y límites. Se corrigen respuestas que mezclaban la etapa REST en memoria con datos/campos de JPA o de la integrada: REST comienza vacío y no incorpora categoría ni versión.

Doce recorridos ejecutables arrancan los cuatro JAR en procesos locales aislados y muestran peticiones/respuestas esperadas. Comprueban configuración, validación, Location, paginación, conflictos, versiones, permisos, CSRF, correlación, salud, métricas y precio histórico de pedidos. No imprimen credenciales y usan H2 en las etapas persistentes del recorrido.

La verificación exige los desarrollos, detecta marcas internas/codificación dañada y ejecuta los recorridos además de las pruebas y contratos existentes. Puede recibir un settings Maven local mediante CURSO_MAVEN_SETTINGS sin incorporar configuración personal al repositorio.

Se mantienen Java 21, Spring Boot 4.1.1 y las dependencias/artifactId/version de los cuatro proyectos ejecutables. La actualización profundiza la enseñanza y sus comprobaciones sin cambiar contratos ni esquema de la API.

[Recorridos guiados](docs/RECORRIDOS_GUIADOS.md) · [Resultados y límites](docs/VERIFICACION.md)

## Base 2.1

Cuatro etapas independientes: fundamentos, REST en memoria, JPA y API integrada. Pedidos/detalles con estados, precio al confirmar, cancelación, migración V4 y control de versión. Se conserva la matriz CI con H2/PostgreSQL y la prueba Docker de Linux; consulta sus resultados por commit.
