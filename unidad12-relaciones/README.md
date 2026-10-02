# Unidad 12 — Entidades y relaciones

## Relaciones
- @OneToOne;
- @OneToMany;
- @ManyToOne;
- @ManyToMany.

## Antes de anotar
Modela la cardinalidad en datos.

## Fetch
EAGER/LAZY afecta cuándo se carga información. No cambies a EAGER para “arreglar” errores sin entender costo.

## Cascada
Cascade no significa “siempre ALL”. Define qué operaciones del ciclo de vida deben propagarse.

## JSON
Relaciones bidireccionales expuestas directamente pueden producir ciclos. DTO evita acoplar serialización al modelo JPA.

## Reto
Modela Pedido→DetallePedido y justifica propiedad, cascada y navegación.
