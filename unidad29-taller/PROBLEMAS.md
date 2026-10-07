# Taller integrador: del catálogo al pedido

Parte de la API ejecutable y crea una rama de práctica. Conserva el contrato existente; desarrolla las ampliaciones de forma gradual. Entrega cada punto con solicitud/respuesta ficticia, código, pruebas y explicación de decisiones. El repositorio incluye la solución ejecutable del catálogo; pedidos es una ampliación que debes implementar.

## 1. Catálogo verificable

Ejecuta un ciclo crear, consultar, actualizar con versión y eliminar. Prueba nombre vacío, precio negativo, SKU repetido, categoría inexistente, página inválida y versión antigua. Explica 400/404/409 y demuestra que el caso rechazado no deja cambios parciales. Consulta [soluciones](SOLUCIONES.md) y FlujoIntegrationTest.

## 2. Pedidos y detalles

Diseña pedido (id, estado, fecha, versión) y detalle (producto, cantidad, precio unitario capturado). Define estados BORRADOR, CONFIRMADO y CANCELADO, cantidades positivas y total calculado. Captura el precio al confirmar según tu contrato; no calcules pedidos históricos a partir del precio actual del catálogo. Decide si cancelación es permitida después de confirmar y explica por qué. Evita referencias JSON circulares mediante DTO.

## 3. Transacción

Confirma el pedido y sus detalles en una sola transacción. Una cantidad inválida debe impedir toda la operación. Prueba rollback y dos confirmaciones concurrentes con versión. No asumas que @Transactional en una llamada interna al mismo objeto activa un proxy nuevo.

## 4. Integración externa

Usa una URL configurada y un servidor simulado. Comprueba éxito, 404, 429, 503, timeout y JSON incompatible. Justifica cuándo repetir GET y por qué no repetir un cobro sin un contrato de idempotencia. Distingue timeout por intento y límite total del proceso.

## 5. Acceso

Especifica quién lee, crea y confirma pedidos. Conserva CSRF si usas credenciales que un navegador reenvía automáticamente. Prueba anónimo con token, lector con token y editor sin token. Documenta qué falta para identidad pública y TLS.

## 6. Observabilidad

Relaciona una petición con su log usando X-Request-Id. No registres autorización, token o cuerpos sensibles. Demuestra health mínimo y métricas protegidas. Define una métrica de confirmaciones y su significado; evita etiquetas con identificadores de pedido o usuario.

## 7. Carga

Mide una lectura paginada en tu entorno local con scripts/carga.py. Registra configuración, calentamiento, concurrencia, número de peticiones, latencia media/p95 y errores. Repite después de un cambio justificado. No conviertas una mejora local en promesa de capacidad de producción.

## 8. Entrega

Añade migraciones nuevas para pedidos y prueba PostgreSQL real. Empaqueta y ejecuta el JAR con configuración externa. Construye imagen, usa usuario sin privilegios y prueba Compose. Documenta cómo conservar/restaurar datos; eliminar volúmenes no es un procedimiento normal de actualización.

## Evidencia final

Contrato y estados, diagrama de relaciones, reglas/transacciones, código, pruebas positivas/negativas/concurrencia, comandos reproducibles y análisis de observabilidad/carga. Usa la [rúbrica](../unidad30-proyecto-final/RUBRICA.md).
