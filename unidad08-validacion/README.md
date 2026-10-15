# Unidad 08: Validación de entrada y reglas de negocio

**[Desarrollo del código y resolución paso a paso](DESARROLLO.md)**

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

**Proyecto de esta unidad:** [laboratorios/rest-memoria](../laboratorios/rest-memoria/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

En esta etapa no existen categoriaId, version ni credenciales. Si el enunciado menciona esos campos, resuelve primero el caso REST y completa esa comparación al llegar a JPA/integración.

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

## Caso desarrollado: Validar sin una base de datos

@Valid activa las reglas del DTO: SKU con formato, nombre no vacío y precio positivo con hasta dos decimales. @Digits rechaza 1.001 antes del servicio. La verificación de categoría comienza en JPA, porque la etapa REST no la modela.

### Ejecutar y comprender

1. Prepara el [entorno de tu sistema](../docs/ENTORNO.md).
2. Sigue el [laboratorio completo](LABORATORIO.md), que identifica código, prueba y resultado.
3. Ejecuta desde la raíz:

```text
mvn -f laboratorios/rest-memoria/pom.xml "-Dtest=ProductoRestTest#validaDatos" test
```

4. Resuelve la [práctica](PRACTICA.md).
5. Compara después con las [soluciones razonadas](SOLUCIONES.md).

### Reto explicado

Prueba precio 1.001 y un SKU ya existente en REST; explica 400 y 409. Al llegar a JPA, añade categoría 99999 inexistente y explica 404.

El objetivo es justificar una decisión con evidencia. No necesitas memorizar todas las anotaciones del proyecto avanzado para estudiar esta unidad.

## Continuar el curso

- **Unidad anterior:** [Unidad 07: DTO y mapeo](../unidad07-dto/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 09: Manejo global de errores](../unidad09-errores/README.md)

---

### ¿Necesitas apoyo con este tema?

Si te bloqueaste con la práctica, necesitas revisar tu entorno o adaptar lo aprendido a tu proyecto, podemos trabajarlo en una sesión personalizada.

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2008%20%E2%80%94%20Validaci%C3%B3n%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad08-validacion%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
