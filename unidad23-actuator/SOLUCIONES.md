# Soluciones razonadas 23: Separar salud del proceso y disponibilidad de dependencias

**Proyecto de esta unidad:** [ejemplos/api-productos](../ejemplos/api-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

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

## Resolución desarrollada

### Vivo no equivale a listo

La configuración readiness incluye db. Si PostgreSQL deja de responder, /actuator/health/readiness debe informar DOWN (normalmente 503), mientras liveness puede continuar UP porque el proceso está vivo. No incluyas una dependencia externa inestable en liveness si eso produce reinicios en cascada.

En un entorno de práctica Compose, consulta ambos endpoints, detén solo db, vuelve a consultar y reinicia db. Registra status y cuerpo. El healthcheck de la imagen PostgreSQL comprueba disponibilidad del servidor, no todas las reglas de tu aplicación. No se promete que el cliente que falla cambie automáticamente readiness.
