# Soluciones razonadas 07: Separar entrada, salida y entidad persistente

## Decisión de referencia

ProductoRequest no admite id ni version; ProductoUpdateRequest exige version; ProductoResponse incluye datos de categoría sin devolver la entidad lazy. El mapeo ocurre dentro de la transacción del servicio y open-in-view=false impide depender de una sesión JPA abierta durante serialización. Los records son útiles como DTO, pero no convierten automáticamente todo objeto en inmutable si contienen referencias mutables.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `FlujoIntegrationTest#creaConsultaActualizaYElimina` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Justifica por qué no se devuelve Producto directamente desde el controller.

**Solución y justificación:** Evita filtrar campos internos, proxies y relaciones como contrato accidental. La respuesta incluye categoriaId/categoria y una versión explícita. El cliente no decide id generado ni estado interno enviando un DTO de creación.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)
