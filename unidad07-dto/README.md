# Unidad 07: DTO y mapeo

**[Desarrollo del código y resolución paso a paso](DESARROLLO.md)**

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

**Proyecto de esta unidad:** [laboratorios/rest-memoria](../laboratorios/rest-memoria/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

En esta etapa no existen categoriaId, version ni credenciales. Si el enunciado menciona esos campos, resuelve primero el caso REST y completa esa comparación al llegar a JPA/integración.

## Qué aprenderás
Diseñar modelos de entrada/salida independientes del dominio/persistencia y mapearlos explícitamente.

# 1. Tres modelos pueden coexistir

```text
HTTP Request DTO
      ↓
modelo dominio/aplicación
      ↓
entidad persistencia
      ↓
HTTP Response DTO
```

No siempre necesitas cuatro clases distintas para todo, pero sí comprender las responsabilidades.

# 2. Request de creación

```java
public record CrearProductoRequest(
    String nombre,
    BigDecimal precio
) {}
```

No incluye `id` si el cliente no debe decidirlo.

# 3. Response

```java
public record ProductoResponse(
    Long id,
    String nombre,
    BigDecimal precio
) {}
```

Puede excluir campos internos y añadir datos derivados necesarios para contrato.

# 4. ¿Por qué no entidad?

Exponer entidad JPA:
- acopla contrato/esquema;
- puede exponer campos;
- complica relaciones/serialización;
- permite entrada sobre propiedades que no deberían modificarse;
- dificulta evolución independiente.

# 5. Mapeo manual

```java
static ProductoResponse toResponse(Producto p) {
    return new ProductoResponse(
        p.getId(),
        p.getNombre(),
        p.getPrecio()
    );
}
```

Para modelos pequeños es transparente y fácil de depurar.

# 6. Herramientas de mapeo

Pueden reducir código repetitivo, pero añaden configuración/generación.

No introduzcas una librería solo para evitar tres líneas.

# 7. DTO distintos por operación

Crear:
```text
nombre, precio
```

Actualizar precio:
```text
precio
```

Respuesta:
```text
id, nombre, precio, ...
```

Un “ProductoDto universal” puede terminar aceptando campos que no aplican.

# 8. Versionado/evolución

Separar DTO permite cambiar persistencia sin cambiar inmediatamente contrato público, y viceversa.

No elimina todo impacto: debes gestionar semántica/versionado conscientemente.

# 9. Práctica guiada

Diseña:
- CrearProductoRequest;
- ActualizarPrecioRequest;
- ProductoResponse.

Marca quién puede escribir cada campo.

# 10. Errores frecuentes
- entidad como DTO;
- un DTO gigante para todo;
- mapper con reglas de negocio ocultas;
- librería de mapeo prematura;
- campos internos en respuesta.

# 11. Reto
Diseña contratos de creación/consulta distintos y mapeo probado.

# 12. Autoevaluación
1. ¿DTO vs entidad?
2. ¿Por qué request/response distintos?
3. ¿Mapeo manual está mal?
4. ¿Qué riesgo tiene DTO universal?
5. ¿Separación elimina todo acoplamiento?

# 13. Checklist
- [ ] Diseño frontera HTTP.
- [ ] No expongo entidad.
- [ ] Mapeo explícitamente.
- [ ] DTO por caso de uso cuando aporta.

Continúa con validación.


---

## Caso desarrollado: Separar entrada y salida

ProductoRequest solo recibe sku, nombre y precio; ProductoResponse añade el id asignado por el servidor. Son records, no beans ni entidades JPA. Al llegar a JPA se incorporan categoría y versión al contrato.

### Ejecutar y comprender

1. Prepara el [entorno de tu sistema](../docs/ENTORNO.md).
2. Sigue el [laboratorio completo](LABORATORIO.md), que identifica código, prueba y resultado.
3. Ejecuta desde la raíz:

```text
mvn -f laboratorios/rest-memoria/pom.xml "-Dtest=ProductoRestTest#contratoCRUD" test
```

4. Resuelve la [práctica](PRACTICA.md).
5. Compara después con las [soluciones razonadas](SOLUCIONES.md).

### Reto explicado

Justifica por qué no se devuelve Producto directamente desde el controller.

El objetivo es justificar una decisión con evidencia. No necesitas memorizar todas las anotaciones del proyecto avanzado para estudiar esta unidad.

## Continuar el curso

- **Unidad anterior:** [Unidad 06: ResponseEntity, headers y códigos HTTP](../unidad06-respuestas-http/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 08: Validación de entrada y reglas de negocio](../unidad08-validacion/README.md)

---

### ¿Necesitas apoyo con este tema?

Si te bloqueaste con la práctica, necesitas revisar tu entorno o adaptar lo aprendido a tu proyecto, podemos trabajarlo en una sesión personalizada.

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2007%20%E2%80%94%20DTO%20y%20mapeo%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad07-dto%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
