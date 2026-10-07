# Soluciones razonadas 13: Filtrar y paginar sin perder el significado del resultado

## Decisión de referencia

findByNombreContainingIgnoreCase filtra texto; Page incluye consulta de conteo además de los datos; Slice permite preguntar si hay una página siguiente sin total exacto. El curso expone metadatos propios y un límite 100. El orden por id evita empate indeterminado, pero no convierte varias páginas en una fotografía estable si llegan filas entre peticiones. No se pagina un fetch join de colección sin analizar multiplicación de filas.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `ProductoRepositoryTest#filtroYPaginaEstable` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Compara Page y Slice para un feed y un reporte que necesita total.

**Solución y justificación:** Feed puede usar Slice o cursor; reporte con total requiere Page o un cálculo explícito. La búsqueda de a obtiene Algoritmos, Bases de datos y Teclado; la primera página de dos contiene los dos libros y total=3. Usa datos y SQL para comprobar.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)
