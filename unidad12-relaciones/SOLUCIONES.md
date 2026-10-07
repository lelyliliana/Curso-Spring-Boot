# Soluciones razonadas 12: Decidir relaciones, fetch y acciones de borrado

## Decisión de referencia

Producto es dueño del ManyToOne mediante categoria_id; la FK es obligatoria. Fetch LAZY no equivale a prometer cero consultas extra, y JPA puede usar proxies o consultas adicionales. No hay cascada REMOVE de producto a categoría: eliminar un producto no debe borrar la categoría compartida. La FK impide borrar una categoría con hijos. @EntityGraph permite cargar categoría para el reporte sin serializar proxies.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `ProductoRepositoryTest#categoriaConHijosProtegida` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Explica cascada JPA y ON DELETE y modela Pedido/Detalle sin copiar CascadeType.ALL indiscriminadamente.

**Solución y justificación:** La cascada JPA propaga operaciones del EntityManager; una acción FK la aplica la base. Pedido puede poseer sus detalles, pero producto compartido no se borra al quitar una línea. Guarda precio histórico en detalle, no lo recalcules desde catálogo. Define la regla antes del mapeo.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)
