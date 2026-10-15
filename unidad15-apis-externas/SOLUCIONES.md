# Soluciones razonadas 15: Consumir una API con límite y contrato propio

**Proyecto de esta unidad:** [ejemplos/api-productos](../ejemplos/api-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

ProveedorClient usa RestClient y un HttpClient con tiempos de conexión/lectura. La URL base es configuración, el SKU restringido forma solo un segmento y el proveedor se simula localmente en pruebas. Un 200 con JSON del tipo esperado todavía requiere validar SKU y precio. No se llama Internet para que una prueba de tu aplicación pase.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `ProveedorClientTest#contratoValido` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Traduce proveedor 404, 429, 503 y JSON inválido al contrato local.

**Solución y justificación:** Proveedor 404 produce NoEncontrado y 404 local;429 produce ProveedorNoDisponible y 503 local sin retry. Con dos intentos, un 503 seguido de 200 puede terminar correctamente; un 503 permanente hace dos llamadas y falla. JSON inválido o cotización de otro SKU se rechazan. El cliente no convierte fallos en precio 0 ni usa un proveedor público para probar la regla.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Traducir fallos sin depender de internet

| Respuesta del proveedor | Respuesta local | Reintento de referencia |
|---|---|---|
| 404 | 404 NOT_FOUND | No |
| 429 | 503 PROVIDER_UNAVAILABLE | No; el ejemplo no implementa Retry-After |
| 503 | 503 si se agotan intentos | Acotado para GET |
| JSON inválido | 503 PROVIDER_UNAVAILABLE | No convierte basura en un producto |

ProveedorClientTest inicia un servidor HTTP local y cambia status/cuerpo. Ejecuta esa clase y lee noEncontrado, rateLimitNoReintenta, falloPermanente y jsonInvalido. El 503 local informa que esta dependencia no permite completar la consulta; no debe devolver su cuerpo sin validar.

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa integrado`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
