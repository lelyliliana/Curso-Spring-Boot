# Soluciones razonadas 10: Asignar una responsabilidad concreta a cada capa

**Proyecto de esta unidad:** [laboratorios/rest-memoria](../laboratorios/rest-memoria/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

En esta etapa no existen categoriaId, version ni credenciales. Si el enunciado menciona esos campos, resuelve primero el caso REST y completa esa comparación al llegar a JPA/integración.

## Decisión de referencia

Controller adapta transporte; Service delimita crear/actualizar/eliminar y transacciones; Repository expresa consultas; Producto protege invariantes; ProveedorClient adapta HTTP externo. El servicio devuelve DTO sin ResponseEntity y no conoce status. CategoriaController es deliberadamente un CRUD pequeño de catálogo que usa repository directamente; explica ese compromiso antes de añadir una capa vacía.

El código completo y sus imports están en [REST en memoria](../laboratorios/rest-memoria/README.md). La prueba `ProductoRestTest#actualizarRechazaSkuAjeno` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Ubica la comprobación SKU, el UNIQUE y el 409 en capas y explica la carrera.

**Solución y justificación:** Service puede rechazar un duplicado conocido pronto; UNIQUE es la garantía concurrente; advice traduce DataIntegrityViolationException. Dos solicitudes pueden pasar existsBySku; solo una debe confirmar. Transacción debe abarcar el caso de uso, no cada llamada aislada.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Reparto de responsabilidades

1. Controller valida forma HTTP y convierte entrada/respuesta.
2. Service comprueba si el SKU existe y decide el caso de uso.
3. Base aplica UNIQUE incluso si dos peticiones pasaron la comprobación previa.
4. Advice convierte DataIntegrityViolationException en 409.

En REST synchronized protege el mapa de una JVM; no garantiza unicidad entre dos procesos. En PostgreSQL dos creaciones simultáneas con SKU RACE-01 producen 201 y 409 según scripts/verificar.py. Consultar primero y guardar después sin UNIQUE dejaría abierta la carrera.
