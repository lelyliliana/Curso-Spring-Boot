# Soluciones razonadas 14: Evolucionar un esquema con migraciones

**Proyecto de esta unidad:** [laboratorios/jpa-productos](../laboratorios/jpa-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

V1 crea tablas, FK y UNIQUE; V2 agrega activo y un índice; Hibernate ddl-auto=validate verifica mapeo sin modificar estructura; Flyway aplica versiones y registra checksum. V3 es carga ficticia solo en perfil dev. No edites una migración ya aplicada: crea la siguiente. H2 facilita comenzar, pero no demuestra compatibilidad PostgreSQL; el verificador arranca el mismo JAR contra una base PostgreSQL nueva.

El código completo y sus imports están en [laboratorios/jpa-productos](../laboratorios/jpa-productos/README.md). La prueba `FlujoIntegrationTest` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Explica qué ocurre al promover el JAR a prod y por qué no se promueve también la base dev.

**Solución y justificación:** Se promueve el JAR y la configuración adecuada, no la base H2 de demostración. JPA en prod aplica V1/V2; la integrada aplica V1/V2/V4 y no V3 dev. DB_URL,DB_USER,DB_PASSWORD deben corresponder a la base de destino. PostgreSQL se verifica con el modo --postgres sobre una base local aislada; un resultado H2 no demuestra por sí solo el comportamiento de ese motor.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Promoción reproducible

1. Construye jpa-productos.jar con mvn -f laboratorios/jpa-productos/pom.xml verify.
2. Crea una base PostgreSQL local de práctica.
3. Configura DB_URL, DB_USER y DB_PASSWORD externamente.
4. Ejecuta con --spring.profiles.active=prod.
5. Consulta flyway_schema_history y /api/productos: en la base nueva no hay datos ficticios.

JPA aplica V1/V2. La API integrada añade V4 (pedidos), mientras V3 permanece solo en db/dev. Promueves código/migraciones, no el contenido H2. Un cambio de clave o puerto no requiere recompilar. Si una migración ya fue aplicada, añade otra; no cambies su checksum para ocultar diferencias.

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa jpa`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
