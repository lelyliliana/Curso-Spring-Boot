# Soluciones razonadas 21: Comprobar colaboración de capas y un flujo completo

**Proyecto de esta unidad:** [ejemplos/api-productos](../ejemplos/api-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

La prueba SpringBootTest+MockMvc integra MVC, servicio, JPA, Flyway y H2; sus cambios se revierten en el hilo del test. La comprobación operativa del JAR abre servidor TCP real y no se revierte con @Transactional en el cliente: elimina su recurso y usa una base propia. No dependas del orden de pruebas ni de una base manual compartida.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `FlujoIntegrationTest` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Prueba actualización con una versión vieja y comprueba que no cambió el valor guardado.

**Solución y justificación:** La creación entrega versión 0; el PUT aceptado entrega 1. Reenviar 0 produce 409 y la consulta posterior mantiene precio y versión 1. El recorrido JPA crea y ejecuta esa secuencia en un JAR real. La integración con MockMvc responde otra pregunta complementaria, sin suponer que un rollback del test controle peticiones a un proceso externo.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Versión antigua y valor persistido

1. Crea producto con precio 25.50 y recibe version 0.
2. PUT con precio 30 y version 0 devuelve version 1.
3. Repite el PUT con precio 50 y version 0: espera 409.
4. GET debe devolver precio 30 y version 1.

Ese último paso detecta una implementación que devuelve conflicto después de modificar datos. La comprobación del JAR ya lo realiza en H2/PostgreSQL. La prueba de pedidos versionAntiguaNoCambiaPedido complementa con commit real fuera de una transacción del test.

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa integrado`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
