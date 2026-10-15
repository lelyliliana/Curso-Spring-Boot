# Soluciones razonadas 04: Diseñar una creación antes de escribir anotaciones

**Proyecto de esta unidad:** [laboratorios/rest-memoria](../laboratorios/rest-memoria/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

En esta etapa no existen categoriaId, version ni credenciales. Si el enunciado menciona esos campos, resuelve primero el caso REST y completa esa comparación al llegar a JPA/integración.

## Decisión de referencia

La etapa REST conserva productos en un mapa. POST válido responde 201 con Location; repetir SKU genera Conflicto y el advice devuelve 409. GET de un id que no existe genera NoEncontrado y devuelve 404. No se necesita base, usuario ni token.

El código completo y sus imports están en [REST en memoria](../laboratorios/rest-memoria/README.md). La prueba `ProductoRestTest#skuUnico` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Define contrato para un SKU duplicado y un id ausente.

**Solución y justificación:** POST válido produce 201 con Location. El segundo POST del mismo SKU produce 409, code CONFLICT; GET de un id ausente produce 404, code NOT_FOUND. El duplicado no crea otra fila. En REST la unicidad se protege con el monitor y mapa; la garantía SQL entre procesos comienza en JPA y no debe atribuirse a esta etapa.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Contrato resuelto

| Situación | Operación | Status | Código |
|---|---|---:|---|
| SKU existente | POST /api/productos | 409 | CONFLICT |
| Id inexistente | GET /api/productos/{id} | 404 | NOT_FOUND |
| Producto nuevo | POST /api/productos | 201 | No es error; Location apunta al recurso |

En REST usa producto-memoria.json con --sin-csrf para crear y repetir. En JPA usa producto.json con --sin-csrf. En la API integrada añade --usuario editor y conserva CSRF. No cambies el status para ocultar un conflicto ni reveles el mapa/SQL interno.

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa rest`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
