# Soluciones razonadas 07: Separar entrada, salida y entidad persistente

**Proyecto de esta unidad:** [laboratorios/rest-memoria](../laboratorios/rest-memoria/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

En esta etapa no existen categoriaId, version ni credenciales. Si el enunciado menciona esos campos, resuelve primero el caso REST y completa esa comparación al llegar a JPA/integración.

## Decisión de referencia

ProductoRequest solo recibe sku, nombre y precio; ProductoResponse añade el id asignado por el servidor. Son records, no beans ni entidades JPA. Al llegar a JPA se incorporan categoría y versión al contrato.

El código completo y sus imports están en [REST en memoria](../laboratorios/rest-memoria/README.md). La prueba `ProductoRestTest#contratoCRUD` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Justifica por qué no se devuelve Producto directamente desde el controller.

**Solución y justificación:** Evita filtrar campos internos, proxies y relaciones como contrato accidental. La respuesta incluye categoriaId/categoria y una versión explícita. El cliente no decide id generado ni estado interno enviando un DTO de creación.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Respuesta explícita

REST devuelve ProductoResponse(id, sku, nombre, precio). JPA/integración devuelve además categoría, versión y activo mediante ProductoResponse.de. Una entidad JPA tiene relaciones lazy y ciclo de vida transaccional; el cliente necesita un contrato JSON estable, no proxies ni el grafo de entidades.

Lee ProductoDto y localiza el método de antes de que termine la transacción de buscar/listar. Para una nueva propiedad interna (por ejemplo, nota de auditoría), no la añadas al DTO si el contrato no necesita exponerla. Compara el JSON HTTP con la clase entidad, no solo el nombre de ambas.
