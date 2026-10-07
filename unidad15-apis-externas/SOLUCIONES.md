# Soluciones razonadas 15: Consumir una API con límite y contrato propio

## Decisión de referencia

ProveedorClient usa RestClient y un HttpClient con tiempos de conexión/lectura. La URL base es configuración, el SKU restringido forma solo un segmento y el proveedor se simula localmente en pruebas. Un 200 con JSON del tipo esperado todavía requiere validar SKU y precio. No se llama Internet para que una prueba de tu aplicación pase.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `ProveedorClientTest#contratoValido` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Traduce proveedor 404, 429, 503 y JSON inválido al contrato local.

**Solución y justificación:** 404 se traduce a cotización no encontrada; 429/503 e inválido a 503 PROVIDER_UNAVAILABLE. No publiques cuerpo del proveedor ni sus credenciales. La cotización externa no modifica el precio local automáticamente.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)
