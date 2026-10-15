# Soluciones razonadas 12: Decidir relaciones, fetch y acciones de borrado

**Proyecto de esta unidad:** [laboratorios/jpa-productos](../laboratorios/jpa-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

Producto es dueño del ManyToOne mediante categoria_id; la FK es obligatoria. Fetch LAZY no equivale a prometer cero consultas extra, y JPA puede usar proxies o consultas adicionales. No hay cascada REMOVE de producto a categoría: eliminar un producto no debe borrar la categoría compartida. La FK impide borrar una categoría con hijos. @EntityGraph permite cargar categoría para el reporte sin serializar proxies.

El código completo y sus imports están en [laboratorios/jpa-productos](../laboratorios/jpa-productos/README.md). La prueba `ProductoRepositoryTest#categoriaConHijosProtegida` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Explica cascada JPA y ON DELETE y modela Pedido/Detalle sin copiar CascadeType.ALL indiscriminadamente.

**Solución y justificación:** En catálogo, Producto referencia Categoria sin cascada de borrado hacia ella. En pedidos, Pedido posee DetallePedido y este referencia un Producto compartido. La cascada JPA PERSIST actúa al guardar el grafo; ON DELETE CASCADE de la FK actúa en la base al borrar el padre. La prueba detalleNoBorraProductoCompartido comprueba que no se destruye el catálogo.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Modelo implementado del reto

Abre [Pedido](../ejemplos/api-productos/src/main/java/com/lelyliliana/productos/Pedido.java) y [DetallePedido](../ejemplos/api-productos/src/main/java/com/lelyliliana/productos/DetallePedido.java).

| Relación | Operación propagada | Motivo |
|---|---|---|
| Pedido → detalles | PERSIST; orphanRemoval | Son partes del pedido |
| Detalle → producto | Ninguna cascada REMOVE | Producto es compartido |
| FK detalle.pedido_id | ON DELETE CASCADE | La base elimina detalles al borrar el pedido |
| FK detalle.producto_id | Restricción | No borrar un producto referenciado |

La prueba detalleNoBorraProductoCompartido borra un pedido en una transacción real, comprueba cero detalles y consulta el producto todavía existente. La API no ofrece borrado de pedidos porque conserva historial; la eliminación del test sirve para comprobar el modelo.

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa jpa`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
