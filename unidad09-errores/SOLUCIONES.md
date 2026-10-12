# Soluciones razonadas 09: Representar errores sin divulgar información interna

**Proyecto de esta unidad:** [laboratorios/rest-memoria](../laboratorios/rest-memoria/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

En esta etapa no existen categoriaId, version ni credenciales. Si el enunciado menciona esos campos, resuelve primero el caso REST y completa esa comparación al llegar a JPA/integración.

## Decisión de referencia

ApiErrorHandler usa ProblemDetail para 400/404/409/503 y fallos inesperados 500. ResponseEntityExceptionHandler conserva manejo de errores del protocolo como JSON ilegible, tipo incorrecto, método no permitido o formato no soportado. No se retorna e.getMessage de una excepción SQL. Los errores de la cadena de seguridad requieren handlers propios porque ocurren antes de MVC.

El código completo y sus imports están en [REST en memoria](../laboratorios/rest-memoria/README.md). La prueba `ProductoRestTest#erroresNoFiltranInternos` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Escribe qué debe conocer un cliente de un conflicto sin recibir SQL o stack trace.

**Solución y justificación:** status=409, code=CONFLICT y un detalle estable permiten actuar. El request id correlaciona diagnóstico; no reemplaza un mensaje útil. JSON malformado es 400, no 500; 405 conserva el header Allow de la infraestructura MVC.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Cuerpo útil para el cliente

```json
{"status":409,"code":"CONFLICT","detail":"El estado actual impide la operación"}
```
ProblemDetail puede añadir title, type e instance; este fragmento muestra los campos relevantes, no una comparación rígida del cuerpo completo. El cliente puede volver a leer el recurso y pedir al usuario revisar el conflicto. No debe recibir nombre de tabla, sentencia SQL, contraseña ni stack trace. REST ofrece un detalle específico de SKU; la API integrada usa un detalle general para conflictos de integridad/versión.
