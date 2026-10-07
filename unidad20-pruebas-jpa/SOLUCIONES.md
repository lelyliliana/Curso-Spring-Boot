# Soluciones razonadas 20: Probar una consulta y una restricción del modelo

## Decisión de referencia

DataJpaTest utiliza base embebida aislada y rollback por prueba. Flyway se importa para que el esquema/migraciones sean el objeto de la prueba, no un DDL creado libremente por Hibernate. saveAndFlush fuerza una restricción en el momento observado. categoriaConHijosProtegida demuestra la FK; duplicadoReal demuestra UNIQUE. Una prueba que inserta y consulta sin límite de negocio puede limitarse a repetir funcionalidad del framework.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `ProductoRepositoryTest` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Enumera los riesgos que H2 no cubre y cómo se verifica PostgreSQL.

**Solución y justificación:** Tipos, dialecto, consultas y comportamiento de locks pueden diferir. scripts/verificar.py crea una base PostgreSQL exclusiva, ejecuta migraciones con el JAR y realiza el flujo HTTP; no afirma que H2 en MODE=PostgreSQL sea PostgreSQL.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)
