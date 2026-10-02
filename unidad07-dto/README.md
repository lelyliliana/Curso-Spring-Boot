# Unidad 07 — DTO y mapeo

## Por qué DTO
La entidad de persistencia y el contrato HTTP tienen responsabilidades distintas.

```java
record CrearProductoRequest(String nombre, BigDecimal precio) {}
record ProductoResponse(Long id, String nombre, BigDecimal precio) {}
```

## Ventajas
- contrato explícito;
- evita exponer campos internos;
- permite evolucionar persistencia/API por separado;
- validación específica de entrada.

## Mapeo
Puede ser manual o con herramienta. Para modelos pequeños, mapeo manual es transparente.

## Reto
Diseña request/response distintos para creación y consulta.
