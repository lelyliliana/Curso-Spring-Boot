# Soluciones razonadas 23: Separar salud del proceso y disponibilidad de dependencias

## Decisión de referencia

health público no revela components. Liveness no se acopla al proveedor opcional; readiness incluye db además de readinessState. Actuator expone solo health,info,metrics y protege los no públicos con OPS_READ. UP no prueba cada regla del negocio. Sacar todas las réplicas de tráfico por un proveedor opcional caído puede empeorar una degradación.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `FlujoIntegrationTest#saludPublicaYMetricasProtegidas` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Explica qué cambia si la DB falla y el proceso sigue vivo.

**Solución y justificación:** Readiness puede marcar servicio no listo por db; liveness debe seguir reflejando que el proceso no necesita reinicio por esa dependencia externa. El health agregado y los grupos son distintos endpoints; no confundas sus resultados.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)
