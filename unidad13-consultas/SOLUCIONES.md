# Soluciones razonadas 13: Filtrar y paginar sin perder el significado del resultado

**Proyecto de esta unidad:** [laboratorios/jpa-productos](../laboratorios/jpa-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

findByNombreContainingIgnoreCase filtra texto; Page incluye consulta de conteo además de los datos; Slice permite preguntar si hay una página siguiente sin total exacto. El curso expone metadatos propios y un límite 100. El orden por id evita empate indeterminado, pero no convierte varias páginas en una fotografía estable si llegan filas entre peticiones. No se pagina un fetch join de colección sin analizar multiplicación de filas.

El código completo y sus imports están en [laboratorios/jpa-productos](../laboratorios/jpa-productos/README.md). La prueba `ProductoRepositoryTest#filtroYPaginaEstable` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Compara Page y Slice para un feed y un reporte que necesita total.

**Solución y justificación:** La referencia usa Sort.by("id") y una página acotada entre 1 y 100. El filtro no distingue mayúsculas. Total y páginas describen todas las coincidencias; contenido describe la página pedida. La prueba filtroYPaginaEstable aporta evidencia con una población definida. El orden estable no promete una instantánea entre peticiones si otros procesos cambian los datos.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Elegir Page o Slice

| Necesidad | Elección | Justificación |
|---|---|---|
| Reporte con total y número de páginas | Page | Necesita información de cantidad |
| Feed con siguiente bloque | Slice | Basta saber si hay siguiente bloque |

Para un feed puedes declarar un método repository que devuelve Slice<Producto> y recibir Pageable. Para el reporte conserva Page del ejemplo. En ambos usa orden estable por id y límite máximo 100. Observa SQL antes de afirmar que eliminaste el count: una optimización puede evitarlo en algunas páginas y el comportamiento depende del método/proveedor. No inventes un total en la respuesta de Slice.

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa jpa`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
