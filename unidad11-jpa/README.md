# Unidad 11: Spring Data JPA y persistencia

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

**Proyecto de esta unidad:** [laboratorios/jpa-productos](../laboratorios/jpa-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

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


---

## Caso desarrollado: Observar SQL, identidad y unidad transaccional

@Entity mapea Producto; @Id y @GeneratedValue usan identidad; repository genera consultas; @Transactional rodea caso de uso. save no significa que cada cambio se haya confirmado: flush envía SQL y commit confirma la unidad. readOnly es una indicación de optimización, no un sistema de permisos que impide toda escritura. @Version detecta actualizaciones concurrentes cuando la base aplica su comparación de versión.

### Ejecutar y comprender

1. Prepara el [entorno de tu sistema](../docs/ENTORNO.md).
2. Sigue el [laboratorio completo](LABORATORIO.md), que identifica código, prueba y resultado.
3. Ejecuta desde la raíz:

```text
mvn -f laboratorios/jpa-productos/pom.xml "-Dtest=ProductoRepositoryTest#relacionPersistida" test
```

4. Resuelve la [práctica](PRACTICA.md).
5. Compara después con las [soluciones razonadas](SOLUCIONES.md).

### Reto explicado

Describe dónde se confirma la creación y por qué se usa saveAndFlush.

El objetivo es justificar una decisión con evidencia. No necesitas memorizar todas las anotaciones del proyecto avanzado para estudiar esta unidad.

## Continuar el curso

- **Unidad anterior:** [Unidad 10: Capas y responsabilidades](../unidad10-capas/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 12: Relaciones JPA y cardinalidad](../unidad12-relaciones/README.md)

---

### ¿Necesitas apoyo con este tema?

Si te bloqueaste con la práctica, necesitas revisar tu entorno o adaptar lo aprendido a tu proyecto, podemos trabajarlo en una sesión personalizada.

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2011%20%E2%80%94%20Spring%20Data%20JPA%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad11-jpa%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
