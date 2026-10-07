# Soluciones razonadas 06: Controlar status, headers y cuerpo

## Decisión de referencia

ResponseEntity.created recibe una URI y genera 201/Location. ResponseEntity.noContent no debe contener un objeto de error ni el producto eliminado. Content-Type describe el cuerpo; Accept pide formatos que el cliente entiende. Las respuestas JSON se contrastan en propiedades relevantes, no con una cadena rígida sensible al orden de campos.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `ProductoControllerTest#creacionContrato` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Comprueba Location, Content-Type y version en una creación, además del status.

**Solución y justificación:** creacionContrato exige /api/productos/5 y version=0 en la slice. En ejecución real utiliza el id devuelto; las secuencias pueden dejar huecos. Un cliente sigue Location, no supone que el siguiente id será 5.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)
