# Soluciones razonadas 21: Comprobar colaboración de capas y un flujo completo

**Proyecto de esta unidad:** [ejemplos/api-productos](../ejemplos/api-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

La prueba SpringBootTest+MockMvc integra MVC, servicio, JPA, Flyway y H2; sus cambios se revierten en el hilo del test. La comprobación operativa del JAR abre servidor TCP real y no se revierte con @Transactional en el cliente: elimina su recurso y usa una base propia. No dependas del orden de pruebas ni de una base manual compartida.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `FlujoIntegrationTest` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Prueba actualización con una versión vieja y comprueba que no cambió el valor guardado.

**Solución y justificación:** Lee version=0, actualiza a otro precio y recibe version=1, reenvía version=0 y espera 409. Consulta después y conserva el precio confirmado de version=1. @Version añade defensa ante carreras que ocurren después de leer la versión.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

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
