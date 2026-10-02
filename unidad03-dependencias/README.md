# Unidad 03 — Inyección de dependencias y componentes

## Qué aprenderás
Hacer dependencias explícitas, elegir estereotipos y resolver ambigüedades sin acoplar clases innecesariamente.

# 1. Dependencia oculta

```java
class PedidoService {
    private final PedidoRepository repo =
        new PedidoRepositoryReal();
}
```

La clase decide implementación. Probar/sustituir se vuelve difícil.

# 2. Constructor injection

```java
@Service
class PedidoService {
    private final PedidoRepository repository;

    PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }
}
```

La dependencia es explícita y obligatoria.

Con un único constructor, normalmente no necesitas `@Autowired` explícito.

# 3. Field injection

```java
@Autowired
private PedidoRepository repository;
```

oculta dependencias en campos y dificulta crear el objeto fuera de Spring.

Preferimos constructor como punto de partida.

# 4. Estereotipos

- `@Component`: componente general.
- `@Service`: servicio.
- `@Repository`: persistencia y traducción de ciertas excepciones.
- `@RestController`: adaptador HTTP.

La anotación comunica rol; no reemplaza diseño.

# 5. Múltiples implementaciones

Si hay dos beans compatibles, Spring puede necesitar criterio.

Herramientas:
- `@Qualifier`;
- `@Primary`;
- configuración explícita.

Antes de añadir anotaciones, revisa si el diseño realmente necesita esa elección.

# 6. Dependencia circular

A depende de B y B de A.

Puede impedir crear contexto y, más importante, suele señalar responsabilidades mal separadas.

No la “soluciones” cambiando a field/lazy injection sin analizar el modelo.

# 7. Beans de terceros

```java
@Bean
Clock clock() {
    return Clock.systemUTC();
}
```

Una dependencia Clock inyectada facilita pruebas deterministas de tiempo.

# 8. Práctica guiada

Refactoriza:

```text
PedidoService → new RepositorioReal
```

a:

```text
PedidoService → contrato Repositorio
Spring → implementación
```

Después prueba PedidoService sin contexto Spring.

# 9. Errores frecuentes
- field injection por defecto;
- new de infraestructura en service;
- @Component en objetos de dominio;
- Qualifier para esconder diseño confuso;
- dependencia circular ignorada.

# 10. Reto
Servicio con repositorio y Clock inyectados, probado sin levantar Spring.

# 11. Autoevaluación
1. ¿Por qué constructor?
2. ¿Autowired es necesario con único constructor?
3. ¿Qué comunica Repository?
4. ¿Qué ocurre con dos beans?
5. ¿Qué puede indicar un ciclo?

# 12. Checklist
- [ ] Dependencias explícitas.
- [ ] Constructor injection.
- [ ] Beans con rol.
- [ ] Diagnostico ambigüedad/ciclos.

Continúa con HTTP.
