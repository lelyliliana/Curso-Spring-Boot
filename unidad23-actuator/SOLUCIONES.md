# Soluciones razonadas 23: Separar salud del proceso y disponibilidad de dependencias

**Proyecto de esta unidad:** [ejemplos/api-productos](../ejemplos/api-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

health público no revela components. Liveness no se acopla al proveedor opcional; readiness incluye db además de readinessState. Actuator expone solo health,info,metrics y protege los no públicos con OPS_READ. UP no prueba cada regla del negocio. Sacar todas las réplicas de tráfico por un proveedor opcional caído puede empeorar una degradación.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `FlujoIntegrationTest#saludPublicaYMetricasProtegidas` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Explica qué cambia si la DB falla y el proceso sigue vivo.

**Solución y justificación:** Si la base falla, readiness puede marcar no listo porque incluye db; liveness no debe convertirse automáticamente en fallo de proceso por esa dependencia. Health agregado y grupos tienen endpoints distintos. La referencia limita detalles públicos y protege métricas. El recorrido comprueba la política de acceso; no afirma haber provocado toda clase de avería de base.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Vivo no equivale a listo

La configuración readiness incluye db. Si PostgreSQL deja de responder, /actuator/health/readiness debe informar DOWN (normalmente 503), mientras liveness puede continuar UP porque el proceso está vivo. No incluyas una dependencia externa inestable en liveness si eso produce reinicios en cascada.

En un entorno de práctica Compose, consulta ambos endpoints, detén solo db, vuelve a consultar y reinicia db. Registra status y cuerpo. El healthcheck de la imagen PostgreSQL comprueba disponibilidad del servidor, no todas las reglas de tu aplicación. No se promete que el cliente que falla cambie automáticamente readiness.

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa integrado`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
