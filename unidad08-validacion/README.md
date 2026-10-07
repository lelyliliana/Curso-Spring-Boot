# Unidad 08: Validación de entrada y reglas de negocio

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

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


---

## Caso desarrollado: Distinguir forma válida de regla de negocio

@Valid activa restricciones del DTO: SKU en mayúsculas, nombre no vacío, precio mínimo 0.01 con hasta dos decimales y categoría positiva. Esas restricciones no verifican existencia de categoría ni unicidad bajo concurrencia. El dominio vuelve a validar invariantes para llamadas fuera de MVC. La base añade constraints. Ninguna capa conoce reglas que no se escribieron.

### Ejecutar y comprender

1. Prepara el [entorno de tu sistema](../docs/ENTORNO.md).
2. Sigue el [laboratorio completo](LABORATORIO.md), que identifica código, prueba y resultado.
3. Ejecuta desde la raíz:

```text
mvn -f ejemplos/api-productos/pom.xml "-Dtest=ProductoControllerTest#postInvalido" test
```

4. Resuelve la [práctica](PRACTICA.md).
5. Compara después con las [soluciones razonadas](SOLUCIONES.md).

### Reto explicado

Prueba precio 1.001, categoría 99999 y SKU ya existente; explica los tres errores.

El objetivo es justificar una decisión con evidencia. No necesitas memorizar todas las anotaciones del proyecto avanzado para estudiar esta unidad.

## Continuar el curso

- **Unidad anterior:** [Unidad 07: DTO y mapeo](../unidad07-dto/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 09: Manejo global de errores](../unidad09-errores/README.md)
