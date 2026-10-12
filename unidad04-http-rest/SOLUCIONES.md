# Soluciones razonadas 04: Diseñar una creación antes de escribir anotaciones

**Proyecto de esta unidad:** [laboratorios/rest-memoria](../laboratorios/rest-memoria/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

En esta etapa no existen categoriaId, version ni credenciales. Si el enunciado menciona esos campos, resuelve primero el caso REST y completa esa comparación al llegar a JPA/integración.

## Decisión de referencia

POST /api/productos crea un recurso con 201 y Location. GET no pide crear ni modificar recursos. PUT reemplaza los campos del contrato de actualización y exige la versión leída; se utiliza 409 si esa versión está desactualizada. DELETE exitoso devuelve 204 sin cuerpo; repetirlo puede devolver 404 y seguir siendo idempotente en el efecto (el recurso permanece ausente). REST no equivale a cualquier JSON sobre HTTP: incluye restricciones arquitectónicas además de convenciones de rutas.

El código completo y sus imports están en [REST en memoria](../laboratorios/rest-memoria/README.md). La prueba `ProductoRestTest#skuUnico` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Define contrato para un SKU duplicado y un id ausente.

**Solución y justificación:** Duplicado: 409 application/problem+json con code CONFLICT. Ausente: 404 NOT_FOUND. La clave única de la base resuelve también una carrera que dos comprobaciones existsBySku no pueden evitar por sí solas.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

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
