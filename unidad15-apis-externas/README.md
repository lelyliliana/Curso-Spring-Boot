# Unidad 15 — Consumo de APIs externas

## Flujo
```text
tu servicio → HTTP → servicio externo
           ← respuesta/error
```

## Cliente
Spring ofrece clientes HTTP. La elección depende de versión/estilo de aplicación.

## Separa
Crea un cliente/adaptador externo en lugar de mezclar HTTP externo dentro de reglas de negocio.

## Valida respuesta
Código, formato y campos pueden fallar.

## Reto
Consume una API externa mediante un componente aislado y traduce su respuesta a un modelo interno.
