# Unidad 03 — Inyección de dependencias y componentes

## Constructor
```java
@Service
class PedidoService {
    private final PedidoRepository repository;

    PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }
}
```

La inyección por constructor hace dependencias explícitas y facilita pruebas.

## Estereotipos
- @Component;
- @Service;
- @Repository;
- @Controller/@RestController.

Las anotaciones comunican rol, pero no sustituyen buen diseño.

## Evita
Field injection como patrón por defecto.

## Reto
Refactoriza una clase que crea internamente su repositorio para recibirlo por constructor.
