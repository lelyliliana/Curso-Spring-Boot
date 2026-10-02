# Unidad 16 — Timeouts, errores y resiliencia básica

## Timeout
Toda dependencia remota puede tardar demasiado.

Considera:
- conexión;
- lectura/respuesta;
- límite total según cliente.

## Retry
Reintentar no siempre es seguro. Pregunta si la operación es idempotente y si el fallo es transitorio.

## Circuit breaker
Puede evitar insistir sobre un servicio claramente degradado, pero añade estados y configuración.

## Fallback
No inventes datos “normales” para ocultar un fallo. Un fallback debe preservar semántica.

## Reto
Diseña comportamiento para timeout, 404 externo, 429 y 500.
