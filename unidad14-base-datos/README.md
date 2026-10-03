# Unidad 14 — Base de datos real y migraciones

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

## Qué aprenderás
Configurar PostgreSQL, versionar el esquema y separar ciclo de vida de datos del arranque de la aplicación.

# 1. H2 no es PostgreSQL

Una base en memoria es útil para demos/tests específicos, pero diferencias de:
- tipos;
- SQL;
- constraints;
- funciones;
- transacciones;
- locking;
pueden ocultar problemas.

Para integración que deba representar producción, usa una estrategia más fiel.

# 2. Datasource

Configuración conceptual:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
```

No publiques secretos.

# 3. ddl-auto

Opciones de Hibernate pueden crear/actualizar esquema.

Para producción no confíes en `create`/`create-drop` o actualizaciones automáticas como estrategia de evolución.

# 4. Migración

Herramientas como Flyway/Liquibase mantienen cambios versionados.

Ejemplo Flyway:

```text
V1__crear_producto.sql
V2__agregar_codigo_unico.sql
```

Cada cambio queda trazable.

# 5. V1

```sql
CREATE TABLE producto (
  id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  nombre text NOT NULL,
  precio numeric(12,2) NOT NULL CHECK (precio > 0)
);
```

# 6. V2

```sql
ALTER TABLE producto
ADD COLUMN codigo text;

-- estrategia de backfill si existen datos
-- luego restricciones apropiadas
```

En una base con datos, añadir NOT NULL de inmediato puede fallar. Diseña migración compatible con estado existente.

# 7. Migraciones inmutables

Una migración ya aplicada en entornos compartidos no debería editarse como si nunca hubiera existido. Crea una nueva corrección.

# 8. Rollback

No todas las herramientas/estrategias manejan rollback igual.

En producción, “deshacer” un cambio de esquema/datos puede requerir migración forward, backup/restauración o plan específico.

No prometas rollback automático universal.

# 9. Práctica guiada

1. PostgreSQL local/controlado.
2. V1 esquema.
3. inicia app.
4. inserta datos.
5. V2 sin perderlos.
6. verifica constraint.

# 10. Errores frecuentes
- H2 como prueba definitiva de PostgreSQL;
- ddl-auto update en producción sin control;
- editar V1 aplicada;
- secreto en properties;
- migración que ignora datos existentes.

# 11. Reto
Dos migraciones consecutivas conservando datos y añadiendo una restricción.

# 12. Autoevaluación
1. ¿H2 = PostgreSQL?
2. ¿Por qué migraciones?
3. ¿Editar V1 aplicada?
4. ¿Qué riesgo tiene NOT NULL sobre tabla poblada?
5. ¿Rollback siempre automático?

# 13. Checklist
- [ ] Configuro DB externamente.
- [ ] Versiono esquema.
- [ ] Conservo datos.
- [ ] No dependo de ddl-auto destructivo.

Continúa con APIs externas.


---

## Continuar el curso

- **Unidad anterior:** [Unidad 13 — Consultas, paginación y N+1](../unidad13-consultas/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 15 — Consumo de APIs externas](../unidad15-apis-externas/README.md)
