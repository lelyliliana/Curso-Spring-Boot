# Unidad 20 — Pruebas de persistencia JPA

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
