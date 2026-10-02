# Unidad 08 — Validación

## Bean Validation
```java
record CrearProductoRequest(
    @NotBlank String nombre,
    @Positive BigDecimal precio
) {}
```

Controller:
```java
ResponseEntity<?> crear(@Valid @RequestBody CrearProductoRequest request)
```

## Dos tipos de reglas
**Validación de forma:** requerido, tamaño, rango.

**Regla de negocio:** por ejemplo, código único o transición permitida.

No metas todas las reglas de negocio en anotaciones.

## Reto
Clasifica diez reglas entre validación de entrada y regla de dominio.
