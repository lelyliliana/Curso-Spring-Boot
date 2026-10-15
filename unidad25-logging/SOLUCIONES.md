# Soluciones razonadas 25: Correlacionar solicitudes sin registrar credenciales

**Proyecto de esta unidad:** [ejemplos/api-productos](../ejemplos/api-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

RequestIdFilter admite header de hasta 64 caracteres alfanuméricos/guion, genera UUID si no cumple y devuelve X-Request-Id. MDC se limpia en finally para no contaminar otro trabajo del hilo. El log registra método y status; no Authorization ni cuerpo. La correlación no autentica al emisor y MDC no se propaga automáticamente a tareas asíncronas o llamadas externas.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `FlujoIntegrationTest#requestIdAcotado` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Envía un id inválido y verifica que no se refleja literalmente en el log/respuesta.

**Solución y justificación:** El id válido se conserva y el excesivamente largo se reemplaza por UUID. El recorrido utiliza un header largo válido como transporte, de modo que llega al filtro. La expresión excluye saltos de línea en un valor aceptado. La respuesta puede verificarse directamente; revisar contenido de logs y propagación asíncrona requiere evidencias adicionales, sin registrar credenciales.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Rechazar un identificador como evidencia

RequestIdFilter acepta un id acotado; si llega uno demasiado largo o con caracteres inválidos, genera un UUID. Ejecuta FlujoIntegrationTest#requestIdAcotado: envía 100 caracteres y espera patrón UUID en el header de respuesta.

Para la inspección manual, envía X-Request-Id con espacios/caracteres inválidos mediante tu cliente HTTP y compara el header con los logs locales. Nunca permitas saltos de línea del usuario como contenido de logs. El filtro limpia MDC al terminar para no asignar a otra petición el identificador anterior.

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa integrado`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
