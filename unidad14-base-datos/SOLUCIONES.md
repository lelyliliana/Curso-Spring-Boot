# Soluciones razonadas 14: Evolucionar un esquema con migraciones

## Decisión de referencia

V1 crea tablas, FK y UNIQUE; V2 agrega activo y un índice; Hibernate ddl-auto=validate verifica mapeo sin modificar estructura; Flyway aplica versiones y registra checksum. V3 es carga ficticia solo en perfil dev. No edites una migración ya aplicada: crea la siguiente. H2 facilita comenzar, pero no demuestra compatibilidad PostgreSQL; el verificador arranca el mismo JAR contra una base PostgreSQL nueva.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `FlujoIntegrationTest` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Explica qué ocurre al promover el JAR a prod y por qué no se promueve también la base dev.

**Solución y justificación:** prod carga db/migration, exige DB_URL/DB_USER/DB_PASSWORD y no crea datos ficticios V3. La base dev usa ubicación adicional db/dev; no debe reutilizarse como producción. Verifica V1/V2 y ddl validate en PostgreSQL con la prueba operativa.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)
