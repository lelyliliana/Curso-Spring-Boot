# Soluciones razonadas 30: Construir una API propia justificando decisiones

**Proyecto de esta unidad:** [ejemplos/api-productos](../ejemplos/api-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

La API de productos es referencia completa, no un proyecto universal. Su alcance no incluye pagos, inventario físico, login web, JWT, OAuth ni una plataforma de identidad real; Basic con cuentas en memoria es educativo. Una ampliación necesita reglas nuevas, no más anotaciones. La rúbrica exige reconstrucción, contratos, datos íntegros, pruebas y operación.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `FlujoIntegrationTest` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Extiende con pedido y detalle guardando precio histórico y cuidando concurrencia.

**Solución y justificación:** Define estados, cantidades y moneda; la referencia no implementa stock físico. No reutilices precio actual para una venta pasada. Protege cambios con transacción, restricciones y control de concurrencia; entrega test de rollback y de dos solicitudes que intentan confirmar el mismo pedido. Si esas reglas no se implementan, no declares la ampliación terminada.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Punto de partida completo del proyecto final

La [solución de pedidos](../docs/PEDIDOS.md) ya implementa Pedido, DetallePedido, DTO, servicio, controller, FK, cantidades, precio histórico y version. PedidoIntegrationTest#precioHistorico comprueba el precio antes/después de confirmar y un cambio posterior del catálogo; rollbackDeConfirmacionReviertePrimerDetalle comprueba que un fallo no captura el primer precio parcialmente.

Para tu proyecto cambia el dominio y sus reglas, no solamente nombres. Entrega contrato, pruebas de rollback/conflicto y comandos de reconstrucción. El ejemplo demuestra control concurrente del pedido. No afirma reservar inventario físico; si decides añadir stock, protege esa entidad compartida y prueba dos pedidos que disputen las mismas unidades.
