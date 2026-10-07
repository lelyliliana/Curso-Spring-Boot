# Unidad 13: Consultas, paginación y N+1

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

## Qué aprenderás
Diseñar consultas Spring Data desde necesidades reales, paginar y observar el SQL para detectar N+1.

# 1. Derived query

```java
List<ProductoEntity>
findByNombreContainingIgnoreCase(String nombre);
```

Spring deriva consulta del nombre.

Es cómodo para consultas sencillas. Si el nombre se vuelve una frase ilegible, considera `@Query`, Specifications u otra estrategia.

# 2. JPQL

```java
@Query("""
    select p
    from ProductoEntity p
    where p.activo = true
""")
List<ProductoEntity> activos();
```

JPQL consulta entidades/atributos, no tablas SQL directamente.

# 3. Consulta nativa

Puede ser necesaria para capacidades específicas de la base, pero aumenta acoplamiento al SQL/dialecto.

No es “mala”; úsala con una razón.

# 4. Paginación

```java
Page<ProductoEntity> findAll(Pageable pageable);
```

Una API con miles/millones de filas no debería devolver todo por defecto.

# 5. Page vs Slice

`Page` normalmente incluye total, lo cual puede requerir consulta count.

`Slice` solo necesita saber si hay siguiente segmento y puede evitar ese total cuando no lo necesitas.

Elige según contrato.

# 6. Orden

No confíes en orden accidental de la base.

Incluye Sort/ORDER BY cuando el contrato lo requiera.

Para paginación estable, considera criterios deterministas de desempate.

# 7. N+1

Cargas 100 pedidos y luego accedes a cliente de cada uno.

Podrías obtener:
```text
1 consulta pedidos
+ 100 consultas clientes
```

No lo diagnostiques por intuición: observa SQL.

# 8. Soluciones posibles

Según caso:
- fetch join;
- EntityGraph;
- proyección;
- consulta DTO;
- batch fetching/configuración.

No cambies todo a EAGER.

# 9. Índices

Si filtras frecuentemente:
```sql
WHERE cliente_id = ?
ORDER BY fecha
```

revisa índices en la base.

JPA no diseña índices por ti según carga real.

# 10. Práctica guiada

Implementa búsqueda:
- nombre;
- activo;
- página;
- orden.

Activa SQL y cuenta consultas.

Introduce una relación y reproduce N+1; luego corrígelo conscientemente.

# 11. Errores frecuentes
- método derivado gigantesco;
- Page cuando no necesitas total;
- paginar sin orden estable;
- EAGER contra N+1;
- optimizar sin mirar SQL.

# 12. Reto
Endpoint paginado y evidencia del número de consultas antes/después de una mejora.

# 13. Autoevaluación
1. ¿Derived query?
2. ¿JPQL vs SQL?
3. ¿Page vs Slice?
4. ¿Qué es N+1?
5. ¿EAGER lo resuelve siempre?
6. ¿Por qué índice sigue importando?

# 14. Checklist
- [ ] Diseño consultas legibles.
- [ ] Pagino.
- [ ] Ordeno determinísticamente.
- [ ] Observo SQL/N+1.

Continúa con migraciones.


---

## Caso desarrollado: Filtrar y paginar sin perder el significado del resultado

findByNombreContainingIgnoreCase filtra texto; Page incluye consulta de conteo además de los datos; Slice permite preguntar si hay una página siguiente sin total exacto. El curso expone metadatos propios y un límite 100. El orden por id evita empate indeterminado, pero no convierte varias páginas en una fotografía estable si llegan filas entre peticiones. No se pagina un fetch join de colección sin analizar multiplicación de filas.

### Ejecutar y comprender

1. Prepara el [entorno de tu sistema](../docs/ENTORNO.md).
2. Sigue el [laboratorio completo](LABORATORIO.md), que identifica código, prueba y resultado.
3. Ejecuta desde la raíz:

```text
mvn -f ejemplos/api-productos/pom.xml "-Dtest=ProductoRepositoryTest#filtroYPaginaEstable" test
```

4. Resuelve la [práctica](PRACTICA.md).
5. Compara después con las [soluciones razonadas](SOLUCIONES.md).

### Reto explicado

Compara Page y Slice para un feed y un reporte que necesita total.

El objetivo es justificar una decisión con evidencia. No necesitas memorizar todas las anotaciones del proyecto avanzado para estudiar esta unidad.

## Continuar el curso

- **Unidad anterior:** [Unidad 12: Relaciones JPA y cardinalidad](../unidad12-relaciones/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 14: Base de datos real y migraciones](../unidad14-base-datos/README.md)
