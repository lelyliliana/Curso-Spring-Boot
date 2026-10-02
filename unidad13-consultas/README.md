# Unidad 13 — Consultas y paginación

## Derived queries
```java
List<Producto> findByNombreContainingIgnoreCase(String nombre);
```

## Page
```java
Page<Producto> findAll(Pageable pageable);
```

## No devuelvas colecciones ilimitadas
Una API real debe considerar volumen.

## N+1
Acceder a relaciones en un bucle puede generar muchas consultas. Observa SQL y diseña consultas según caso.

## Índices
Una consulta frecuente puede requerir apoyo en la base de datos.

## Reto
Implementa búsqueda paginada y registra cuántas consultas SQL produce.
