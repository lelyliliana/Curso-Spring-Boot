# Soluciones razonadas 10: Asignar una responsabilidad concreta a cada capa

**Proyecto de esta unidad:** [laboratorios/rest-memoria](../laboratorios/rest-memoria/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

En esta etapa no existen categoriaId, version ni credenciales. Si el enunciado menciona esos campos, resuelve primero el caso REST y completa esa comparación al llegar a JPA/integración.

## Decisión de referencia

El controller transforma HTTP en una llamada de servicio; el servicio guarda y consulta el mapa con SKU único; el DTO define JSON y el advice traduce excepciones. synchronized protege una JVM; en la siguiente etapa la base añade UNIQUE y transacciones.

El código completo y sus imports están en [REST en memoria](../laboratorios/rest-memoria/README.md). La prueba `ProductoRestTest#actualizarRechazaSkuAjeno` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Ubica la comprobación SKU, el UNIQUE y el 409 en capas y explica la carrera.

**Solución y justificación:** En REST, comprobarSku y el cambio del mapa se protegen con el mismo monitor; el advice devuelve 409. En JPA el servicio comprueba intención, UNIQUE protege el dato entre transacciones y el advice traduce DataIntegrityViolationException. No atribuyas esa restricción SQL al proyecto de memoria. ProductoRestTest.actualizarRechazaSkuAjeno comprueba conservación tras rechazo.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Reparto de responsabilidades

1. Controller valida forma HTTP y convierte entrada/respuesta.
2. Service comprueba si el SKU existe y decide el caso de uso.
3. Base aplica UNIQUE incluso si dos peticiones pasaron la comprobación previa.
4. Advice convierte DataIntegrityViolationException en 409.

En REST synchronized protege el mapa de una JVM; no garantiza unicidad entre dos procesos. En PostgreSQL dos creaciones simultáneas con SKU RACE-01 producen 201 y 409 según scripts/verificar.py. Consultar primero y guardar después sin UNIQUE dejaría abierta la carrera.

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa rest`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
