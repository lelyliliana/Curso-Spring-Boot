# Unidad 14 — Base de datos y migraciones

## Desarrollo vs producción
Una base en memoria puede servir para ciertos tests/demos, pero no representa todas las características de una base real.

## Migraciones
Herramientas como Flyway/Liquibase permiten versionar cambios de esquema.

## Evita
Confiar en creación automática destructiva del esquema como estrategia de producción.

## Configuración
URL, usuario y contraseña no deben quedar como secretos públicos.

## Reto
Crea una migración inicial y una segunda que añada una restricción/campo sin borrar datos.
