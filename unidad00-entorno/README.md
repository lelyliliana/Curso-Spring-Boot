# Unidad 00 — Entorno y primer proyecto Spring Boot

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

## Qué aprenderás
Crear, ejecutar, probar y diagnosticar una aplicación Spring Boot con Java 21 y Maven.

## Antes de empezar
Debes manejar Java, POO, Maven y pruebas básicas. Para persistencia posterior se recomienda el curso de Bases de Datos y SQL.

# 1. Comprueba herramientas

```bash
java --version
mvn --version
```

No basta con que ambos existan: revisa qué Java reporta Maven.

# 2. ¿Qué vamos a construir?

Una aplicación backend que:
- inicia un servidor HTTP embebido;
- recibe peticiones;
- ejecuta lógica;
- devuelve respuestas.

Al principio no usaremos base de datos.

# 3. Proyecto

Estructura Maven:

```text
pom.xml
src/
├── main/
│   ├── java/
│   └── resources/
└── test/
    └── java/
```

# 4. Dependencias iniciales

Para las primeras unidades:
- Spring Web;
- Validation;
- Spring Boot Test.

Persistencia se añade cuando llegue JPA.

# 5. Clase principal

```java
@SpringBootApplication
public class Aplicacion {
    public static void main(String[] args) {
        SpringApplication.run(Aplicacion.class, args);
    }
}
```

`@SpringBootApplication` agrupa varias capacidades de configuración/escaneo/autoconfiguración. Las iremos descomponiendo conceptualmente; no necesitas memorizar su implementación interna.

# 6. Ejecutar

```bash
mvn spring-boot:run
```

Observa logs:
- versión;
- puerto;
- contexto;
- tiempo de inicio.

No ignores logs hasta que aparezca “error”.

# 7. Empaquetar

```bash
mvn test
mvn package
```

Después podrás ejecutar el JAR generado según nombre:

```bash
java -jar target/archivo.jar
```

# 8. Puerto

En `application.properties`:

```properties
server.port=8081
```

Si 8080 está ocupado, cambiar puerto es una solución posible, pero primero identifica qué proceso/servicio ocupa el puerto.

# 9. Cinco capas de diagnóstico

Cuando “Spring no funciona”, clasifica:

1. Maven/dependencias;
2. compilación Java;
3. creación del contexto;
4. servidor/puerto;
5. petición HTTP.

Un 404 significa que el servidor probablemente **sí** respondió; no es lo mismo que no iniciar.

# 10. Práctica guiada

1. ejecuta aplicación;
2. identifica puerto;
3. detén;
4. cambia puerto;
5. inicia;
6. ejecuta tests;
7. empaqueta;
8. ejecuta JAR.

# 11. Errores frecuentes
- Maven con Java distinto.
- ejecutar desde carpeta sin pom.
- puerto ocupado.
- clase principal fuera del paquete raíz esperado para escaneo.
- confundir 404 con caída del servidor.

# 12. Reto
Crea aplicación mínima reproducible y documenta comandos exactos para ejecutar/tests/package.

# 13. Autoevaluación
1. ¿Qué inicia SpringApplication.run?
2. ¿Dónde se configura puerto?
3. ¿404 significa servidor caído?
4. ¿Qué hace mvn package?
5. ¿Por qué mirar mvn --version?

# 14. Checklist
- [ ] Inicio aplicación.
- [ ] Cambio configuración.
- [ ] Ejecuto tests.
- [ ] Empaqueto/JAR.
- [ ] Clasifico errores.

Continúa con Spring e IoC.


---

## Continuar el curso

- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 01 — Spring, Spring Boot y contenedor IoC](../unidad01-spring-ioc/README.md)
