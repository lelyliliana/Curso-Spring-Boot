# Soluciones razonadas 25: Correlacionar solicitudes sin registrar credenciales

## Decisión de referencia

RequestIdFilter admite header de hasta 64 caracteres alfanuméricos/guion, genera UUID si no cumple y devuelve X-Request-Id. MDC se limpia en finally para no contaminar otro trabajo del hilo. El log registra método y status; no Authorization ni cuerpo. La correlación no autentica al emisor y MDC no se propaga automáticamente a tareas asíncronas o llamadas externas.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `FlujoIntegrationTest#requestIdAcotado` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Envía un id inválido y verifica que no se refleja literalmente en el log/respuesta.

**Solución y justificación:** requestIdAcotado comprueba id válido y reemplazo por UUID del excesivamente largo. La expresión impide saltos de línea y evita log injection. En asíncrono necesitas propagación explícita de contexto; no confundir un request id local con una traza distribuida completa.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)
