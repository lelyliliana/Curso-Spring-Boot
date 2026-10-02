# Unidad 11 — Spring Data JPA

## Entidad
```java
@Entity
class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // ...
}
```

## Repository
```java
interface ProductoRepository extends JpaRepository<Producto, Long> {}
```

## JPA no elimina SQL
Debes comprender:
- tablas;
- claves;
- consultas;
- transacciones;
- índices;
- cardinalidad.

## Entidad ≠ DTO
No expongas entidades como contrato HTTP por comodidad.

## Reto
Implementa persistencia de un recurso manteniendo DTO separados.
