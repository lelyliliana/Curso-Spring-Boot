# Soluciones razonadas 05: Enlazar rutas y parámetros con casos de uso

## Decisión de referencia

ProductoController conoce DTO y HTTP, y delega al servicio. @PathVariable identifica recurso; @RequestParam pagina/filtra; @RequestBody recibe JSON. La colección devuelve una envoltura propia con contenido y metadatos para evitar exponer la representación interna de Page. El endpoint no acepta una URL arbitraria de proveedor.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `ProductoControllerTest#listaPaginada` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Consulta nombre=base y pagina=0,tamano=2; identifica cuántos productos entran.

**Solución y justificación:** La carga dev tiene Bases de datos como coincidencia. total=1, paginas=1 y contenido con LIB-02. Orden fijo por id. La prueba de slice sustituye el servicio y no demuestra consulta JPA; el test de repository cubre esa frontera.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)
