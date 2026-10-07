# Soluciones razonadas 24: Leer métricas con etiquetas acotadas

## Decisión de referencia

Micrometer registra http.server.requests con método, status y URI normalizada por ruta. Un id de producto o request id como tag puede crear una serie por cada solicitud. Timer cuenta y acumula duración; no todos los backends exponen percentiles automáticamente. Métricas resumen comportamiento; logs correlacionan eventos y trazas conectan fronteras.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `FlujoIntegrationTest#saludPublicaYMetricasProtegidas` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Define cinco métricas y estima cardinalidad.

**Solución y justificación:** Tasa HTTP (contador), errores por clase de status, duración HTTP (timer), conexiones activas de pool (gauge), duración proveedor (timer). Tags método/ruta plantilla/resultado acotado, sin correos ni SKU. Para proveedor habría que agregar instrumentación propia; no se afirma que el ejemplo ya publique ese timer.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)
