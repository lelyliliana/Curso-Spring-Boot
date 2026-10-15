# Soluciones razonadas 09: Representar errores sin divulgar información interna

**Proyecto de esta unidad:** [laboratorios/rest-memoria](../laboratorios/rest-memoria/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

En esta etapa no existen categoriaId, version ni credenciales. Si el enunciado menciona esos campos, resuelve primero el caso REST y completa esa comparación al llegar a JPA/integración.

## Decisión de referencia

El advice de REST representa 400, 404 y 409 mediante ProblemDetail. La respuesta permite actuar al cliente sin exponer estructuras internas. La validación puede incluir campos; errores de protocolo no necesariamente incluyen el mismo code.

El código completo y sus imports están en [REST en memoria](../laboratorios/rest-memoria/README.md). La prueba `ProductoRestTest#erroresNoFiltranInternos` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Escribe qué debe conocer un cliente de un conflicto sin recibir SQL o stack trace.

**Solución y justificación:** Para conflicto comunica 409, code CONFLICT y un detalle estable. La respuesta no debe revelar SQL ni stack trace. ProductoRestTest.erroresNoFiltranInternos comprueba la frontera HTTP. El request id pertenece a la integrada; es una ayuda de correlación, no una capacidad ya presente en el advice del laboratorio REST.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Cuerpo útil para el cliente

```json
{"status":409,"code":"CONFLICT","detail":"El estado actual impide la operación"}
```
ProblemDetail puede añadir title, type e instance; este fragmento muestra los campos relevantes, no una comparación rígida del cuerpo completo. El cliente puede volver a leer el recurso y pedir al usuario revisar el conflicto. No debe recibir nombre de tabla, sentencia SQL, contraseña ni stack trace. REST ofrece un detalle específico de SKU; la API integrada usa un detalle general para conflictos de integridad/versión.

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa rest`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
