# Unidad 05 — Controllers y endpoints

## Controller delgado
```java
@RestController
@RequestMapping("/api/productos")
class ProductoController {
    private final ProductoService service;

    ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    List<ProductoResponse> listar() {
        return service.listar();
    }
}
```

## Responsabilidad
Controller:
- recibe HTTP;
- transforma/valida entrada;
- delega;
- produce respuesta.

No debería contener reglas complejas del negocio.

## Reto
Implementa endpoints CRUD delegando toda regla a un servicio.
