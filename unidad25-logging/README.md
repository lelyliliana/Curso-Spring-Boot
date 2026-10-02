# Unidad 25 — Logging y trazabilidad

## Logging
Registra eventos útiles con contexto.

## Evita
- secretos;
- contraseñas;
- tokens;
- datos personales innecesarios;
- stack traces como respuesta HTTP.

## Correlación
Un identificador de solicitud puede ayudar a seguir un flujo entre logs.

## Niveles
Usa niveles con intención; no conviertas todo en ERROR.

## Reto
Diseña estrategia de logs para creación de pedido y fallo de API externa.
