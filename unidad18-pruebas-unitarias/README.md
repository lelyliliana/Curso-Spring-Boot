# Unidad 18 — Pruebas unitarias de servicios

## Objetivo
Probar reglas sin levantar todo Spring cuando no es necesario.

```java
var repo = mock(ProductoRepository.class);
var service = new ProductoService(repo);
```

## Prueba
- resultado;
- excepción;
- interacción cuando forma parte del contrato.

## No uses @SpringBootTest para todo
Una prueba unitaria puede ser más rápida y localizar mejor el fallo.

## Reto
Prueba servicio con casos normal, inexistente y conflicto.
