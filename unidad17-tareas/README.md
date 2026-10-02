# Unidad 17 — Tareas programadas y procesamiento

## @Scheduled
Permite ejecutar tareas según intervalo/cron.

## Cuidado
Una tarea programada puede:
- solaparse;
- fallar;
- tardar más que su intervalo;
- ejecutarse en varias instancias de la aplicación.

## Idempotencia
Importante cuando una tarea puede repetirse.

## Reto
Diseña una sincronización periódica que registre última ejecución y maneje fallo sin perder trazabilidad.
