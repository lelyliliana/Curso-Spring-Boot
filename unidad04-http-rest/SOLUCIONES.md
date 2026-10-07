# Soluciones razonadas 04: Diseñar una creación antes de escribir anotaciones

## Decisión de referencia

POST /api/productos crea un recurso con 201 y Location. GET no pide crear ni modificar recursos. PUT reemplaza los campos del contrato de actualización y exige la versión leída; se utiliza 409 si esa versión está desactualizada. DELETE exitoso devuelve 204 sin cuerpo; repetirlo puede devolver 404 y seguir siendo idempotente en el efecto (el recurso permanece ausente). REST no equivale a cualquier JSON sobre HTTP: incluye restricciones arquitectónicas además de convenciones de rutas.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `ProductoControllerTest#creacionContrato` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Define contrato para un SKU duplicado y un id ausente.

**Solución y justificación:** Duplicado: 409 application/problem+json con code CONFLICT. Ausente: 404 NOT_FOUND. La clave única de la base resuelve también una carrera que dos comprobaciones existsBySku no pueden evitar por sí solas.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)
