# Unidad 12: Relaciones JPA y cardinalidad

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

## Qué aprenderás
Mapear FK y cardinalidades conscientemente y controlar navegación, fetch, cascada y propiedad de relaciones.

# 1. Modelo relacional primero

```text
PEDIDO 1 ─── N DETALLE_PEDIDO
```

En SQL:
```text
detalle_pedido.pedido_id → pedido.id
```

Solo después elegimos anotaciones.

# 2. ManyToOne

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "pedido_id", nullable = false)
private PedidoEntity pedido;
```

Muchos detalles pertenecen a un pedido.

# 3. OneToMany

Navegación inversa:

```java
@OneToMany(mappedBy = "pedido")
private List<DetalleEntity> detalles = new ArrayList<>();
```

`mappedBy` indica que el otro lado controla el mapeo de la asociación.

No necesitas bidireccionalidad si el caso de uso no requiere navegar ambos sentidos.

# 4. Owning side

En JPA describe el lado que controla la asociación para persistencia.

No significa “dueño del negocio”.

# 5. Cascade

```java
cascade = CascadeType.PERSIST
```

propaga operaciones específicas.

`CascadeType.ALL` no debe usarse por comodidad.

# 6. orphanRemoval

Puede eliminar hijos retirados de una relación cuando la semántica es de composición.

No lo actives si el hijo puede vivir independientemente.

# 7. Fetch

No diseñes apoyándote ciegamente en defaults EAGER/LAZY.

Pregunta qué necesita cada caso de uso y construye la consulta adecuada.

# 8. ManyToMany

Si la relación tiene atributos, la tabla intermedia debe convertirse en entidad.

Pedido-Producto con cantidad/precio necesita `DetallePedido`.

# 9. JSON

Exponer entidades bidireccionales puede producir ciclos, consultas inesperadas y contratos enormes.

DTO desacopla serialización del modelo JPA.

# 10. Helpers

Métodos como `agregarDetalle` pueden mantener ambos lados coherentes en memoria cuando la relación es bidireccional.

# 11. Práctica guiada

Pedido/Detalle:
1. dibuja FK;
2. mapea ManyToOne;
3. decide si necesitas OneToMany;
4. decide cascada;
5. decide orphanRemoval;
6. crea DTO.

# 12. Errores frecuentes
- anotar antes de modelar;
- bidireccional para todo;
- Cascade.ALL automático;
- EAGER para arreglar lazy;
- ManyToMany con atributos ocultos.

# 13. Reto
Modela Pedido→Detalle justificando cada decisión desde el modelo relacional.

# 14. Autoevaluación
1. ¿Dónde vive FK?
2. ¿Qué significa mappedBy?
3. ¿Owning side = dueño del negocio?
4. ¿Cascade ALL siempre?
5. ¿Cuándo entidad intermedia?
6. ¿Por qué DTO?

# 15. Checklist
- [ ] Modelo cardinalidad primero.
- [ ] Mapeo FK.
- [ ] Limito navegación.
- [ ] Justifico cascada/fetch.

Continúa con consultas.


---

## Caso desarrollado: Decidir relaciones, fetch y acciones de borrado

Producto es dueño del ManyToOne mediante categoria_id; la FK es obligatoria. Fetch LAZY no equivale a prometer cero consultas extra, y JPA puede usar proxies o consultas adicionales. No hay cascada REMOVE de producto a categoría: eliminar un producto no debe borrar la categoría compartida. La FK impide borrar una categoría con hijos. @EntityGraph permite cargar categoría para el reporte sin serializar proxies.

### Ejecutar y comprender

1. Prepara el [entorno de tu sistema](../docs/ENTORNO.md).
2. Sigue el [laboratorio completo](LABORATORIO.md), que identifica código, prueba y resultado.
3. Ejecuta desde la raíz:

```text
mvn -f ejemplos/api-productos/pom.xml "-Dtest=ProductoRepositoryTest#categoriaConHijosProtegida" test
```

4. Resuelve la [práctica](PRACTICA.md).
5. Compara después con las [soluciones razonadas](SOLUCIONES.md).

### Reto explicado

Explica cascada JPA y ON DELETE y modela Pedido/Detalle sin copiar CascadeType.ALL indiscriminadamente.

El objetivo es justificar una decisión con evidencia. No necesitas memorizar todas las anotaciones del proyecto avanzado para estudiar esta unidad.

## Continuar el curso

- **Unidad anterior:** [Unidad 11: Spring Data JPA y persistencia](../unidad11-jpa/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 13: Consultas, paginación y N+1](../unidad13-consultas/README.md)
