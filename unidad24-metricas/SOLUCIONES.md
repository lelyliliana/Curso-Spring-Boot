# Soluciones razonadas 24: Leer métricas con etiquetas acotadas

**Proyecto de esta unidad:** [ejemplos/api-productos](../ejemplos/api-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

Micrometer registra http.server.requests con método, status y URI normalizada por ruta. Un id de producto o request id como tag puede crear una serie por cada solicitud. Timer cuenta y acumula duración; no todos los backends exponen percentiles automáticamente. Métricas resumen comportamiento; logs correlacionan eventos y trazas conectan fronteras.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `FlujoIntegrationTest#saludPublicaYMetricasProtegidas` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Define cinco métricas y estima cardinalidad.

**Solución y justificación:** Propón contador de peticiones/errores, timer HTTP, gauge de conexiones y timer de proveedor, con etiquetas acotadas de método/ruta/resultado. Para proveedor declara la instrumentación pendiente. Estima cardinalidad con conjuntos concretos, sin SKU ni request id. No confundas una propuesta con cinco métricas personalizadas ya implementadas o percentiles disponibles en cualquier backend.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Métricas con etiquetas acotadas

| Métrica | Tipo | Etiquetas sugeridas |
|---|---|---|
| Peticiones HTTP | Contador del timer | Ruta plantilla, método, status |
| Latencia HTTP | Timer | Las mismas combinaciones acotadas |
| Conexiones activas | Gauge | Pool conocido |
| Duración del proveedor | Timer adicional | Resultado entre éxito/fallo/timeout |
| Confirmaciones de pedido | Contador adicional | Resultado éxito/conflicto |

Ejemplo: 8 rutas × 4 métodos × 5 grupos de status = hasta 160 combinaciones para esas dimensiones, antes de otras etiquetas/series del timer. Un SKU o request id añadiría valores sin un límite pequeño. Los últimos dos instrumentos son una ampliación propuesta; no declares que ya están registrados solo porque existe Actuator.

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa integrado`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
