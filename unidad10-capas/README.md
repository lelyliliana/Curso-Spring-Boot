# Unidad 10 — Capas y responsabilidades

Una estructura posible:
```text
controller → service → repository
     ↓          ↓
    DTO       dominio
```

No es una ley universal, pero ayuda a separar HTTP, reglas y persistencia.

## Controller
Contrato HTTP.

## Service
Casos de uso/reglas de aplicación.

## Repository
Acceso a persistencia.

## Dominio
Conceptos y reglas que no deberían depender de HTTP.

## Reto
Toma un controller con lógica y distribuye responsabilidades justificando cada movimiento.
