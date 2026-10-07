# Soluciones razonadas 09: Representar errores sin divulgar información interna

## Decisión de referencia

ApiErrorHandler usa ProblemDetail para 400/404/409/503 y fallos inesperados 500. ResponseEntityExceptionHandler conserva manejo de errores del protocolo como JSON ilegible, tipo incorrecto, método no permitido o formato no soportado. No se retorna e.getMessage de una excepción SQL. Los errores de la cadena de seguridad requieren handlers propios porque ocurren antes de MVC.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `ProductoControllerTest#jsonMalformado400` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Escribe qué debe conocer un cliente de un conflicto sin recibir SQL o stack trace.

**Solución y justificación:** status=409, code=CONFLICT y un detalle estable permiten actuar. El request id correlaciona diagnóstico; no reemplaza un mensaje útil. JSON malformado es 400, no 500; 405 conserva el header Allow de la infraestructura MVC.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)
