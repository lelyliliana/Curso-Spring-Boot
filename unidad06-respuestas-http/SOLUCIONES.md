# Soluciones razonadas 06: Controlar status, headers y cuerpo

**Proyecto de esta unidad:** [laboratorios/rest-memoria](../laboratorios/rest-memoria/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

En esta etapa no existen categoriaId, version ni credenciales. Si el enunciado menciona esos campos, resuelve primero el caso REST y completa esa comparación al llegar a JPA/integración.

## Decisión de referencia

ResponseEntity.created establece 201 y Location con el id generado. El GET de ese recurso demuestra que se guardó; DELETE devuelve 204 sin cuerpo. Esta etapa no expone version; el campo se incorpora en JPA.

El código completo y sus imports están en [REST en memoria](../laboratorios/rest-memoria/README.md). La prueba `ProductoRestTest#contratoCRUD` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Comprueba Location y Content-Type en una creación, además del status. Al llegar a JPA, añade la comprobación de version.

**Solución y justificación:** ProductoRestTest.contratoCRUD verifica201, Location y application/json; después consulta, actualiza y elimina. Usa el id recibido. La etapa REST no devuelve version. En JPA una creación incluye version0 y la actualización aceptada la incrementa. DELETE204 tiene cuerpo vacío y no un objeto JSON que deba inventarse para contentar al cliente.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Comprobar más que el status

En ProductoRestTest#contratoCRUD observa isCreated, header Location y contentTypeCompatibleWith. Usa el id de la respuesta para GET; ese recurso debe tener los mismos datos. La etapa REST no devuelve version. Al repetir en JPA/integración, la creación devuelve version 0 y PUT correcto la aumenta a 1. Content-Type application/json y Location del recurso forman parte del contrato de éxito; los errores usan application/problem+json.

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa rest`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
