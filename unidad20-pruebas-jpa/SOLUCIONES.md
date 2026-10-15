# Soluciones razonadas 20: Probar una consulta y una restricción del modelo

**Proyecto de esta unidad:** [ejemplos/api-productos](../ejemplos/api-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

DataJpaTest utiliza base embebida aislada y rollback por prueba. Flyway se importa para que el esquema/migraciones sean el objeto de la prueba, no un DDL creado libremente por Hibernate. saveAndFlush fuerza una restricción en el momento observado. categoriaConHijosProtegida demuestra la FK; duplicadoReal demuestra UNIQUE. Una prueba que inserta y consulta sin límite de negocio puede limitarse a repetir funcionalidad del framework.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `ProductoRepositoryTest` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Enumera los riesgos que H2 no cubre y cómo se verifica PostgreSQL.

**Solución y justificación:** H2 no cubre automáticamente dialecto, tipos ni bloqueos de PostgreSQL. --postgres crea recursos locales aleatorios, arranca los JAR y verifica migraciones y operaciones reales antes de limpiarlos. El resultado de una prueba H2 debe nombrarse como tal. La CI mantiene comprobación PostgreSQL; su configuración no equivale por sí sola a un resultado observado de ese commit.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Comprobación sobre base real

```text
python -m pip install -r requirements.txt
python scripts/verificar.py --postgres
```
Prepara PostgreSQL local y PGHOST/PGPORT/PGUSER según [verificación](../docs/VERIFICACION.md). La comprobación crea identidades/bases temporales propias y las elimina al terminar. Verifica ambas etapas JPA/integrada, migraciones y restricciones; en la integrada también enfrenta creaciones y confirmaciones concurrentes.

H2 no demuestra planes de ejecución PostgreSQL, bloqueos exactos, collation, rendimiento, permisos de producción ni compatibilidad de toda sentencia futura. Añadir una consulta nativa nueva exige probarla en el motor objetivo.

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa integrado`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
