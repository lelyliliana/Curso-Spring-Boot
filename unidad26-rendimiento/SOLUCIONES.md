# Soluciones razonadas 26: Medir antes de modificar consultas o índices

**Proyecto de esta unidad:** [ejemplos/api-productos](../ejemplos/api-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

La consulta paginada conserva orden y usa EntityGraph para categoría. No se concluye rendimiento por tener cuatro filas. scripts/carga.py mide GET público sobre un servidor local elegido, reporta latencia y errores y limita cantidad/concurrencia. No es una prueba distribuida ni demuestra capacidad en producción. Incluye calentamiento y registra versión, datos y hardware al comparar.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `ProductoRepositoryTest#filtroYPaginaEstable` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Propón experimento N+1 y una mejora sin cambiar cinco cosas a la vez.

**Solución y justificación:** Captura SQL y conteo de consultas con datos suficientes, identifica consultas por categoría, compara con EntityGraph y resultados iguales. Después cambia un factor, repite con población comparable y registra buffers/planes de la DB si aplica. Promedio solo puede ocultar cola lenta.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Experimento de una sola variable

1. Usa una copia de práctica con varias categorías/productos y una página de tamaño fijo.
2. En la etapa JPA habilita logging SQL; observa las consultas de listar con @EntityGraph.
3. En otra copia quita solo @EntityGraph de findByNombreContainingIgnoreCase y repite desde un contexto limpio.
4. Compara SELECT de categorías, no solo tiempo; varias filas con la misma categoría pueden reutilizar el contexto y producir menos de N consultas.
5. Restablece @EntityGraph y repite con los mismos datos/tamaño.

Para tiempos usa scripts/carga.py con URL local y número/concurrencia constantes. No compares una primera ejecución fría con otra ya calentada. Una reducción de SELECT puede explicar la mejora; un índice de FK no elimina por sí solo el N+1 de carga de relaciones.
