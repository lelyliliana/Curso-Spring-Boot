# Unidad 01: Spring, Spring Boot y contenedor IoC

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

## Qué aprenderás
Distinguir Spring/Spring Boot, comprender inversión de control, beans y por qué no todo objeto debe administrarlo el contenedor.

# 1. Sin contenedor

```java
Repositorio repo = new RepositorioArchivo();
Servicio servicio = new Servicio(repo);
Controlador controlador = new Controlador(servicio);
```

Nosotros creamos y conectamos objetos.

Eso no es necesariamente malo; de hecho ayuda a comprender lo que Spring automatiza.

# 2. Inversión de control

Con Spring, el contenedor puede:
- crear ciertos objetos;
- resolver dependencias;
- gestionar ciclo de vida/configuración.

```text
Contenedor Spring
├── controller
│    └── service
└── repository
```

La aplicación declara relaciones y el contenedor las ensambla.

# 3. Bean

Un **bean** es un objeto gestionado por el contenedor Spring.

Ejemplo:

```java
@Service
class SaludoService {
    String saludar() {
        return "Hola";
    }
}
```

El objeto de dominio `new Producto(...)` no necesita ser bean solo porque usamos Spring.

# 4. Spring vs Spring Boot

Spring Framework ofrece IoC, web, datos, etc.

Spring Boot facilita construir/ejecutar aplicaciones Spring mediante:
- auto-configuración;
- starters;
- convenciones;
- servidor embebido;
- configuración externa y herramientas operativas.

Boot no reemplaza Spring: lo organiza/facilita.

# 5. Auto-configuración

Spring Boot observa:
- clases disponibles;
- beans;
- propiedades;
- condiciones;

y configura infraestructura cuando aplican determinadas condiciones.

“No es magia” significa que las decisiones tienen reglas y pueden diagnosticarse.

# 6. Component scanning

Anotaciones estereotipo:
- `@Component`;
- `@Service`;
- `@Repository`;
- `@Controller/@RestController`.

Pueden descubrirse mediante escaneo según paquetes/configuración.

No uses una anotación solo para “que Spring la vea” si el objeto no pertenece al contenedor.

# 7. Configuración explícita

También puedes declarar:

```java
@Configuration
class Configuracion {
    @Bean
    Calculador calculador() {
        return new Calculador();
    }
}
```

Útil especialmente para clases de terceros o construcción explícita.

# 8. Ciclo de vida

El contenedor crea beans singleton por defecto en muchos casos, pero existen scopes distintos.

“Singleton de Spring” describe una instancia por contenedor/definición en ese scope, no el patrón singleton global clásico necesariamente.

# 9. Práctica guiada

Clasifica:
- Producto de dominio;
- ProductoService;
- Clock;
- cliente HTTP;
- DTO;
- configuración.

Decide qué debería ser bean y por qué.

# 10. Errores frecuentes
- convertir DTO/entidad en @Component.
- new Servicio dentro del controller.
- “Spring crea cualquier objeto”.
- creer que Boot = framework totalmente distinto.
- usar field injection por comodidad (ver siguiente unidad).

# 11. Reto
Dibuja grafo de beans de una API pequeña y separa objetos administrados de objetos de dominio.

# 12. Autoevaluación
1. ¿Qué es IoC?
2. ¿Qué es bean?
3. ¿Boot reemplaza Spring?
4. ¿Qué hace component scanning?
5. ¿Todo objeto debe ser bean?

# 13. Checklist
- [ ] Distingo Spring/Boot.
- [ ] Comprendo contenedor.
- [ ] Identifico beans.
- [ ] No convierto dominio en infraestructura.

Continúa con configuración.


---

## Caso desarrollado: Ver qué objetos administra el contenedor

FundamentosApplication declara Clock mediante @Bean. SaludoService se descubre por @Service y el constructor recibe Clock y SaludoProperties. Saludo es un record creado por petición, no un bean. El singleton del servicio es por contenedor/definición; debe evitar guardar estado mutable específico de un usuario. ContenedorTest comprueba identidad del bean y existencia de Clock.

### Ejecutar y comprender

1. Prepara el [entorno de tu sistema](../docs/ENTORNO.md).
2. Sigue el [laboratorio completo](LABORATORIO.md), que identifica código, prueba y resultado.
3. Ejecuta desde la raíz:

```text
mvn -f laboratorios/fundamentos/pom.xml "-Dtest=ContenedorTest#beansSingleton" test
```

4. Resuelve la [práctica](PRACTICA.md).
5. Compara después con las [soluciones razonadas](SOLUCIONES.md).

### Reto explicado

Dibuja el grafo del saludo y explica por qué el DTO no lleva @Component.

El objetivo es justificar una decisión con evidencia. No necesitas memorizar todas las anotaciones del proyecto avanzado para estudiar esta unidad.

## Continuar el curso

- **Unidad anterior:** [Unidad 00: Entorno y primer proyecto Spring Boot](../unidad00-entorno/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 02: Configuración, properties y perfiles](../unidad02-configuracion/README.md)
