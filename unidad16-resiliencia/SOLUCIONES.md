# Soluciones razonadas 16: Acotar fallos y reintentos

**Proyecto de esta unidad:** [ejemplos/api-productos](../ejemplos/api-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

Por defecto intentos=1. Para el experimento se admite 2 y se repiten solo respuestas 502/503/504 de este GET. 404, 429, timeout y JSON inválido no se reintentan automáticamente. El ejemplo no implementa circuit breaker ni un presupuesto de tiempo global; dos intentos pueden aumentar la latencia. En un sistema real define backoff/jitter, presupuesto total y política de Retry-After si aplica.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `ProveedorClientTest` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Justifica por qué no copiar este retry a un POST de pago.

**Solución y justificación:** La referencia reintenta solo la lectura GET para502/503/504 y hasta el límite configurado. No implementa un plazo global ni una política de idempotencia de comandos. Una escritura puede haberse completado aunque su respuesta se pierda, por lo que no se debe copiar este retry sin definir cómo reconocer y tratar una repetición. Las pruebas deben comprobar llamadas y resultado.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Por qué el pago necesita otro contrato

Si un POST de cobro tarda demasiado, el timeout no demuestra que el proveedor no haya cobrado. Repetir puede duplicar el efecto. El cliente GET del curso solo consulta y limita intentos; no debe reutilizarse como política automática de pagos.

Para diseñar un pago define clave de idempotencia, deduplicación del proveedor, consulta de estado y tratamiento del resultado incierto. Comprueba los escenarios con un servidor simulado que procesa antes de cortar la respuesta. Si el proveedor no ofrece esas garantías, no afirmes que el retry es seguro.

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa integrado`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
