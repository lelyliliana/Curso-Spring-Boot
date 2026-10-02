# Unidad 11 — Spring Data JPA y persistencia

## Qué aprenderás
Relacionar objetos con tablas, comprender contexto de persistencia/repositorios y evitar tratar JPA como sustituto de SQL.

# 1. Tres nombres distintos

**Jakarta Persistence (JPA):** especificación/API ORM.  
**Hibernate:** implementación común.  
**Spring Data JPA:** abstracciones de repositorio sobre JPA.

No son sinónimos.

# 2. Entidad

```java
@Entity
@Table(name = "producto")
class ProductoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;
}
```

Mapea objeto a tabla/columnas.

# 3. Integridad real

`nullable=false` describe mapeo y puede influir en generación de esquema, pero una base gestionada por migraciones debe contener restricciones reales.

No dependas únicamente de anotaciones Java.

# 4. Repository

```java
interface ProductoRepository
    extends JpaRepository<ProductoEntity, Long> {
}
```

Facilita CRUD, pero el SQL y su costo siguen existiendo.

# 5. Contexto de persistencia

JPA gestiona entidades dentro de un contexto.

Una entidad gestionada modificada dentro de una transacción puede sincronizarse mediante dirty checking al flush/commit sin llamar `save` después de cada setter.

# 6. save no significa siempre INSERT

El comportamiento depende del estado/identidad y de la implementación/contexto.

No uses save como ritual de “confirmar cambios”.

# 7. Transacciones

```java
@Transactional
public void cambiarPrecio(...) {
    ...
}
```

Delimita unidad de trabajo.

Spring suele aplicar transacciones mediante proxies/interceptores. Una llamada interna dentro de la misma instancia puede no atravesar el proxy como esperas.

# 8. Lazy

Una relación lazy puede requerir contexto para cargarse.

No conviertas todo a EAGER para evitar una excepción. Consulta los datos necesarios para el caso de uso.

# 9. Entidad ≠ DTO

El ciclo de vida JPA y el contrato HTTP son responsabilidades distintas.

# 10. Observa SQL

En local/test, habilita logging SQL apropiado y observa SELECT/INSERT/UPDATE y cantidad de consultas.

Relaciona método repository con lo que realmente ocurre en la base.

# 11. Práctica guiada

Producto:
1. tabla/migración;
2. entity;
3. repository;
4. service transaccional;
5. DTO;
6. endpoint;
7. SQL observado.

# 12. Errores frecuentes
- JPA = base;
- JPA elimina SQL;
- save después de cada cambio;
- todo EAGER;
- entidad como JSON;
- Transactional sin comprender proxy.

# 13. Reto
CRUD persistente explicando SQL generado y límites transaccionales.

# 14. Autoevaluación
1. ¿JPA/Hibernate/Spring Data?
2. ¿Qué es contexto?
3. ¿Dirty checking?
4. ¿save siempre INSERT?
5. ¿Por qué no EAGER?
6. ¿Entidad = DTO?

# 15. Checklist
- [ ] Relaciono objeto/tabla.
- [ ] Comprendo contexto/transacción.
- [ ] Observo SQL.
- [ ] Mantengo DTO separado.

Continúa con relaciones.
