# Unidad 08 — Validación de entrada y reglas de negocio

## Qué aprenderás
Usar Bean Validation para forma de entrada y separar esas restricciones de reglas que requieren estado/negocio.

# 1. Validación declarativa

```java
public record CrearProductoRequest(
    @NotBlank String nombre,
    @Positive BigDecimal precio
) {}
```

Controller:

```java
crear(@Valid @RequestBody CrearProductoRequest request)
```

`@Valid` activa validación del objeto en ese punto.

# 2. Forma vs negocio

**Forma/entrada:**
- requerido;
- tamaño;
- patrón;
- rango simple.

**Negocio:**
- código único;
- transición de estado permitida;
- stock suficiente;
- fecha disponible según datos existentes.

Una anotación no debería consultar repositorio solo para meter toda lógica en el DTO.

# 3. Null y anotaciones

`@Positive` no implica necesariamente `@NotNull`.

Combina restricciones según contrato.

```java
@NotNull
@Positive
BigDecimal precio
```

# 4. @NotBlank vs @NotEmpty

Para String:
- NotNull: no null;
- NotEmpty: no null y longitud >0;
- NotBlank: además exige al menos un carácter no whitespace.

Elige semántica correcta.

# 5. Validación anidada

Si un DTO contiene otro objeto que debe validarse, puede requerir `@Valid` en la propiedad/componente correspondiente.

# 6. Mensajes

El mensaje de anotación no debería ser tu único contrato de error externo.

Puedes mapear errores a una estructura estable con código/campo/mensaje.

# 7. Grupos

Bean Validation tiene grupos, pero no los introduzcas automáticamente para resolver DTOs que deberían estar separados por operación.

# 8. Práctica guiada

Clasifica:
- nombre vacío;
- precio negativo;
- SKU duplicado;
- transición ENTREGADO→CREADO;
- email mal formado;
- stock insuficiente.

Decide DTO vs servicio/dominio.

# 9. Errores frecuentes
- toda regla en anotaciones;
- olvidar @Valid;
- Positive esperando rechazar null;
- mismo DTO con grupos complejos cuando DTO separados serían claros;
- mensajes internos como contrato inestable.

# 10. Reto
Valida creación de producto y devuelve errores por campo en formato consistente.

# 11. Autoevaluación
1. ¿Qué hace @Valid?
2. ¿NotBlank?
3. ¿Positive rechaza null por sí sola?
4. ¿SKU único es solo validación de forma?
5. ¿Por qué separar DTO por operación?

# 12. Checklist
- [ ] Valido forma.
- [ ] Mantengo negocio en capa apropiada.
- [ ] Comprendo null.
- [ ] Produzco errores útiles.

Continúa con manejo global.
