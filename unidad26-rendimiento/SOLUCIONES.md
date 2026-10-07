# Soluciones razonadas 26: Medir antes de modificar consultas o índices

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
