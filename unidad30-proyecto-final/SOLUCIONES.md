# Soluciones razonadas 30: Construir una API propia justificando decisiones

**Proyecto de esta unidad:** [ejemplos/api-productos](../ejemplos/api-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

La API de productos es referencia completa, no un proyecto universal. Su alcance no incluye pagos, inventario físico, login web, JWT, OAuth ni una plataforma de identidad real; Basic con cuentas en memoria es educativo. Una ampliación necesita reglas nuevas, no más anotaciones. La rúbrica exige reconstrucción, contratos, datos íntegros, pruebas y operación.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `FlujoIntegrationTest` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Extiende con pedido y detalle guardando precio histórico y cuidando concurrencia.

**Solución y justificación:** La solución completa vive en Pedido,DetallePedido,PedidoService,DTO/controller y migración V4. El precio se captura al confirmar; cancelar conserva el histórico. PedidoIntegrationTest prueba rollback, estados, permisos y contrato. El verificador operativo enfrenta dos confirmaciones con la misma versión y espera un 200 y un 409. No se afirma que la referencia descuente stock físico ni procese pagos.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Punto de partida completo del proyecto final

La [solución de pedidos](../docs/PEDIDOS.md) ya implementa Pedido, DetallePedido, DTO, servicio, controller, FK, cantidades, precio histórico y version. PedidoIntegrationTest#precioHistorico comprueba el precio antes/después de confirmar y un cambio posterior del catálogo; rollbackDeConfirmacionReviertePrimerDetalle comprueba que un fallo no captura el primer precio parcialmente.

Para tu proyecto cambia el dominio y sus reglas, no solamente nombres. Entrega contrato, pruebas de rollback/conflicto y comandos de reconstrucción. El ejemplo demuestra control concurrente del pedido. No afirma reservar inventario físico; si decides añadir stock, protege esa entidad compartida y prueba dos pedidos que disputen las mismas unidades.

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa integrado`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
