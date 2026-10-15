# Soluciones razonadas 29: Integrar contrato, persistencia, seguridad y operación

**Proyecto de esta unidad:** [ejemplos/api-productos](../ejemplos/api-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

Resuelve primero PROBLEMAS.md. La referencia existente demuestra CRUD y actualización con versión, filtro paginado, categoría relacionada, migrations, proveedor con fallos, seguridad y Actuator. No basta que una ruta responda 200: deben coincidir los límites del contrato, las garantías de datos y los permisos. Conserva evidencia de un caso válido y un fallo por frontera.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `FlujoIntegrationTest` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Entrega una solución de taller que otro equipo reconstruya sin preguntarte rutas personales.

**Solución y justificación:** Entrega requisitos, proyecto exacto, comandos desde raíz, preparación de datos, cuerpos JSON y resultados esperados. El índice de desarrollos relaciona cada unidad con fuente y recorrido. scripts/recorridos.py permite repetir doce casos después de construir los cuatro JAR. No exige una ruta personal ni reutiliza una base manual de la usuaria.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Guía razonada de los ocho problemas

| Problema | Referencia y evidencia | Decisión que debe conservarse |
|---|---|---|
| 1. Catálogo | ProductoController, ProductoService, ProductoDto; FlujoIntegrationTest | Mapear dentro de transacción, validar el contrato y rechazar versiones antiguas antes de modificar |
| 2. Pedido | Diseñar entidades/DTO nuevos; ampliar migraciones y pruebas | Pedido posee detalles; producto compartido no es propiedad del detalle. Precio histórico capturado según contrato |
| 3. Transacción | Nuevo servicio de confirmación y prueba de integración | Validar todos los detalles y aplicar estado/precios dentro de una única transacción; una excepción debe revertir todo |
| 4. Proveedor | ProveedorClient y ProveedorClientTest | GET repetible y máximo de intentos explícito. No copiar esa política a pagos |
| 5. Acceso | SecurityConfig y ProductoControllerTest | Probar identidad, autoridad y CSRF de forma independiente, conservando sesión/token |
| 6. Observabilidad | RequestIdFilter, configuración Actuator y FlujoIntegrationTest | Identificador acotado; MDC limpiado al terminar; métricas protegidas |
| 7. Carga | scripts/carga.py | Comparar bajo condiciones equivalentes; explicar p95 y errores antes de proponer índices o caché |
| 8. Entrega | Dockerfile, compose.yml y scripts/verificar.py | Misma aplicación empaquetada, configuración externa y datos persistentes |

### Pedido y confirmación: secuencia de solución

1. Crea migración nueva con tablas pedido/detalle, FK, cantidad positiva, precio capturado y versión del pedido. No edites V1/V2 aplicadas.
2. Usa DTO de creación sin id, estado arbitrario ni total enviado por el cliente. El servidor calcula el total sumando cantidad por precio capturado.
3. El servicio carga el pedido y comprueba versión/estado. BORRADOR puede confirmar; CANCELADO no. Define explícitamente si confirmar nuevamente responde sin cambios o rechaza un conflicto.
4. Valida productos y cantidades, captura precios y establece CONFIRMADO en la transacción. La lectura del catálogo y las reglas deben corresponder a tu contrato de precio.
5. Usa @Version para evitar que dos confirmaciones produzcan actualizaciones silenciosas. Una prueba con dos transacciones debe permitir solo la transición válida; la otra informa conflicto.
6. Prueba fallo en el segundo detalle: el primer detalle tampoco debe quedar confirmado parcialmente. La prueba debe verificar el estado persistido, no solo la excepción.
7. Para cancelar, registra la transición permitida sin borrar el historial. Si incorporas pago/inventario remoto, una transacción de base local no revierte automáticamente ese sistema: necesitas diseñar coordinación/compensación fuera del alcance del catálogo.

Los puntos de pedidos tienen [implementación completa y pruebas](../docs/PEDIDOS.md). La entrega propia debe justificar sus reglas y pruebas; usa la referencia después de intentar tu solución. Las variantes son válidas si el contrato, las reglas y la evidencia son coherentes.

## Resolución desarrollada

### Solución ejecutable del taller

Sigue [PEDIDOS.md](../docs/PEDIDOS.md). Ya incluye clases, migración y pruebas completas de los puntos catálogo, pedido/detalle, transacción y acceso. Las referencias proveedor, logs, carga y Compose completan los otros problemas.

```text
mvn -f ejemplos/api-productos/pom.xml "-Dtest=PedidoIntegrationTest" test
python scripts/verificar.py --postgres
```
El segundo comando requiere la base local de práctica. Un README de entrega debe indicar cómo reproducir cada evidencia; no basta enumerar tecnologías. Los pedidos de referencia no contienen stock ni pago: esos cambios exigen reglas nuevas.

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa integrado`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
