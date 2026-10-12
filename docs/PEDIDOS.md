# Solución completa: pedidos y detalles

Esta solución vive dentro de la API integrada. Estudia primero el catálogo y resuelve el [taller](../unidad29-taller/PROBLEMAS.md) antes de abrir sus clases.

## Paso 1: acordar las reglas

Un pedido nace en BORRADOR con 1–50 productos distintos y cantidad de 1–10000 por detalle. Se utiliza COP como moneda fija del ejemplo; no se mezclan monedas. El total de BORRADOR es 0 y los precios/subtotales son null: todavía no se han capturado.

Al confirmar, se leen los precios actuales del catálogo en la transacción, se guardan en los detalles y se calcula el total. Después un cambio en el catálogo no altera ese precio histórico. No se promete congelar todos los precios del catálogo en un instante global; una regla comercial más exigente necesitaría otro contrato y control de lectura.

| Estado actual | Operación | Resultado |
|---|---|---|
| BORRADOR | Confirmar con versión vigente | CONFIRMADO; captura precios y total |
| BORRADOR | Cancelar con versión vigente | CANCELADO; total 0 |
| CONFIRMADO | Cancelar con versión vigente | CANCELADO; conserva precios y total |
| CONFIRMADO | Confirmar otra vez | 409 |
| CANCELADO | Confirmar o cancelar otra vez | 409 |
| Cualquier estado | Enviar versión antigua | 409; sin cambios |

La cancelación de un confirmado está permitida porque el ejemplo no ejecuta cobros, despachos ni reservas de stock. Cancelar conserva el historial y no elimina el pedido. Una transición correcta aumenta version.

## Paso 2: leer el código en orden

1. [PedidoDto](../ejemplos/api-productos/src/main/java/com/lelyliliana/productos/PedidoDto.java): el cliente envía productos/cantidades; nunca decide estado, total ni precio histórico.
2. [Pedido](../ejemplos/api-productos/src/main/java/com/lelyliliana/productos/Pedido.java): controla transiciones y posee los detalles.
3. [DetallePedido](../ejemplos/api-productos/src/main/java/com/lelyliliana/productos/DetallePedido.java): referencia el producto compartido y captura precio.
4. [PedidoService](../ejemplos/api-productos/src/main/java/com/lelyliliana/productos/PedidoService.java): abre la transacción, resuelve productos, comprueba versión y hace flush.
5. [PedidoController](../ejemplos/api-productos/src/main/java/com/lelyliliana/productos/PedidoController.java): valida HTTP y devuelve 201/Location o respuesta de transición.
6. [V4](../ejemplos/api-productos/src/main/resources/db/migration/V4__pedidos.sql): tablas, FK, cantidades y unicidad por pedido/producto.

Pedido propaga PERSIST a sus detalles y orphanRemoval elimina detalles retirados. No propaga REMOVE a Producto. La FK impide eliminar del catálogo un producto que ya forma parte de un pedido. @Version protege el cambio del pedido; flush envía SQL y la transacción confirma al regresar del servicio.

## Paso 3: ejecutar las pruebas

Desde la raíz:

```text
mvn -f ejemplos/api-productos/pom.xml "-Dtest=PedidoIntegrationTest" test
```

Lee [PedidoIntegrationTest](../ejemplos/api-productos/src/test/java/com/lelyliliana/productos/PedidoIntegrationTest.java). No tiene @Transactional en la clase: cada llamada al servicio realiza commit/rollback real. Se prueba el precio antes/después de confirmar, versión antigua, cancelación, segundo producto inexistente, rollback al fallar el segundo detalle y eliminación sin borrar el producto compartido.

El verificador del JAR añade dos confirmaciones concurrentes en H2 y PostgreSQL real: una devuelve 200 y otra 409. Con Docker se repite el flujo histórico. Esa comprobación complementa la suite Maven; no depende de un proveedor público.

## Paso 4: reproducir por HTTP

Inicia la API integrada según [entorno](ENTORNO.md). Todos los endpoints de pedidos requieren PRODUCT_WRITE (editor); el lector y el acceso anónimo se rechazan también en GET. Es una decisión de permisos del ejemplo, no aislamiento por propietario de pedidos.

En perfil dev, después de reiniciar sin otras modificaciones, crea:

```text
python scripts/peticion.py POST http://localhost:8080/api/pedidos --json datos/pedido.json --usuario editor
```

Espera 201 y Location /api/pedidos/{id}. Usa el id y version devueltos. El archivo version.json contiene 0 para un borrador recién creado:

```text
python scripts/peticion.py POST http://localhost:8080/api/pedidos/1/confirmacion --json datos/version.json --usuario editor
python scripts/peticion.py GET http://localhost:8080/api/pedidos/1 --usuario editor
```

Reemplaza 1 por el id recibido. Con los datos ficticios originales (80 × 2 + 90 × 1), el total confirmado es 250 COP. El precio se captura al confirmar, no al crear el borrador. Para cancelar, actualiza version.json con la versión actual y llama POST /api/pedidos/{id}/cancelacion. Reutilizar 0 después de confirmar debe devolver 409.

En PostgreSQL prod no hay datos ficticios: crea categorías/productos y escribe sus ids en tu copia de pedido.json. No supongas que existen 1 y 2.

## Paso 5: comprobar integridad

Con tu base local de práctica:

```sql
SELECT id, estado, version, total FROM pedido ORDER BY id;
SELECT pedido_id, producto_id, cantidad, precio_unitario FROM detalle_pedido ORDER BY id;
```

Un fallo de confirmación deja BORRADOR, la misma versión, total 0 y precios null. Un pedido confirmado conserva sus precios aunque el catálogo cambie. Para analizar concurrencia usa la comprobación automática, no dos clics sin controlar versión y resultados.

## Paso 6: transferir a un proyecto propio

Implementa otro dominio (préstamos o reservas) con reglas explícitas y pruebas equivalentes. En este ejemplo no hay stock físico, pagos, propietarios, JWT ni identidad pública. Si añades stock, define cuándo se reserva/libera y prueba que dos solicitudes no consumen la misma existencia; @Version del pedido por sí solo no protege el inventario compartido.

[Contrato API](CONTRATO_API.md) · [Proyecto final](../unidad30-proyecto-final/README.md)
