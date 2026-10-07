# Reconocer proyectos Spring Boot 3.5 y 4.1

La base del curso es 4.1.1 con Java 21. Si comparas con un proyecto 3.5, no cambies solo el número del parent sin revisar dependencias e imports. Primero actualiza la línea 3.5, elimina usos deprecados y sigue la guía oficial antes del salto a 4; el curso público no modifica tus repositorios académicos existentes.

| Tema | Base de este curso |
|---|---|
| MVC | spring-boot-starter-webmvc |
| pruebas MVC | spring-boot-starter-webmvc-test |
| WebMvcTest/AutoConfigureMockMvc | org.springframework.boot.webmvc.test.autoconfigure |
| DataJpaTest | org.springframework.boot.data.jpa.test.autoconfigure |
| Flyway | spring-boot-starter-flyway y módulo PostgreSQL |
| dobles de beans | MockitoBean de Spring Framework |
| JSON | Jackson 3, tools.jackson.databind.json.JsonMapper |
| validación/JPA | Jakarta (jakarta.validation, jakarta.persistence) |

Boot 4 modulariza más la configuración y los módulos de test; importar anotaciones antiguas no se corrige añadiendo cualquier dependencia de otra versión. El parent administra el conjunto compatible. Jackson 3 mueve paquetes y cambia APIs; no mezcles un ObjectMapper Jackson 2 y un JsonMapper Jackson 3 sin un requisito y configuración conscientes.

Validar una migración incluye compilar, correr tests, arrancar JAR y comprobar contratos/SQL/configuración. Un cambio de versión no garantiza compatibilidad HTTP automáticamente: el curso usa DTO de página propio para no depender de la serialización interna de Page.

Fuentes: [guía oficial 4.0](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Migration-Guide), [notas 4.1](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.1-Release-Notes), [módulos de test](https://docs.spring.io/spring-boot/reference/testing/test-modules.html).

[Índice](../README.md)
