# Unidad 20: Pruebas de persistencia JPA

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

## Qué aprenderás
Verificar mapeos, constraints y consultas con una base de pruebas adecuada.

# 1. DataJpaTest

```java
@DataJpaTest
class ProductoRepositoryTest { ... }
```

Carga infraestructura enfocada en JPA/repositorios.

# 2. Qué sí prueba

- mapeos;
- queries;
- relaciones;
- paginación;
- constraints según base;
- comportamiento de persistencia.

No necesitas probar que `JpaRepository.findById` funciona en abstracto; prueba lo específico de tu modelo/consulta.

# 3. flush

Algunos errores de constraints aparecen al hacer flush/commit.

En test:
```java
repository.save(entidad);
repository.flush();
```

puede forzar SQL y revelar la violación en el punto esperado.

# 4. Base diferente

H2 y PostgreSQL difieren.

Si la consulta/migración usa capacidades PostgreSQL, un test H2 verde no demuestra compatibilidad.

# 5. Datos reproducibles

Cada test prepara lo mínimo.

Evita depender del orden de ejecución o de una base compartida manualmente.

# 6. Relaciones

Prueba especialmente:
- FK;
- cascada elegida;
- orphan removal;
- consulta con fetch;
- N+1 cuando sea objetivo del test/diagnóstico.

# 7. Migraciones

Una slice JPA puede no representar exactamente el arranque completo con migraciones según configuración.

Para comprobar Flyway+PostgreSQL real, una prueba de integración puede ser más apropiada.

# 8. Práctica guiada

Prueba:
- código único;
- query por nombre;
- relación Pedido/Detalle;
- paginación;
- constraint precio >0.

# 9. Errores frecuentes
- probar framework CRUD trivial;
- no hacer flush;
- H2 como sustituto perfecto;
- datos compartidos;
- confundir slice con integración completa.

# 10. Reto
Suite repository con una consulta no trivial y una restricción real.

# 11. Autoevaluación
1. ¿Qué carga DataJpaTest?
2. ¿Por qué flush?
3. ¿H2 garantiza PostgreSQL?
4. ¿Qué consultas vale probar?
5. ¿Slice prueba migraciones completas siempre?

# 12. Checklist
- [ ] Pruebo mapeos/queries.
- [ ] Fuerzo SQL cuando hace falta.
- [ ] Uso datos aislados.
- [ ] Elijo DB representativa.

Continúa con integración.


---

## Caso desarrollado: Probar una consulta y una restricción del modelo

DataJpaTest utiliza base embebida aislada y rollback por prueba. Flyway se importa para que el esquema/migraciones sean el objeto de la prueba, no un DDL creado libremente por Hibernate. saveAndFlush fuerza una restricción en el momento observado. categoriaConHijosProtegida demuestra la FK; duplicadoReal demuestra UNIQUE. Una prueba que inserta y consulta sin límite de negocio puede limitarse a repetir funcionalidad del framework.

### Ejecutar y comprender

1. Prepara el [entorno de tu sistema](../docs/ENTORNO.md).
2. Sigue el [laboratorio completo](LABORATORIO.md), que identifica código, prueba y resultado.
3. Ejecuta desde la raíz:

```text
mvn -f ejemplos/api-productos/pom.xml "-Dtest=ProductoRepositoryTest" test
```

4. Resuelve la [práctica](PRACTICA.md).
5. Compara después con las [soluciones razonadas](SOLUCIONES.md).

### Reto explicado

Enumera los riesgos que H2 no cubre y cómo se verifica PostgreSQL.

El objetivo es justificar una decisión con evidencia. No necesitas memorizar todas las anotaciones del proyecto avanzado para estudiar esta unidad.

## Continuar el curso

- **Unidad anterior:** [Unidad 19: Pruebas web con MockMvc](../unidad19-mockmvc/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 21: Pruebas de integración](../unidad21-integracion-tests/README.md)
