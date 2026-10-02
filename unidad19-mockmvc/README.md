# Unidad 19 — Pruebas de controllers con MockMvc

## Objetivo
Probar contrato web: rutas, JSON, validación y códigos.

Ejemplo conceptual:
```java
mockMvc.perform(get("/api/productos/1"))
       .andExpect(status().isOk());
```

## Qué probar
- status;
- Content-Type;
- JSON;
- validación;
- errores.

## Reto
Prueba POST válido/inválido y GET inexistente.
