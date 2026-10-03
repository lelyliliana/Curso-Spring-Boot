# Unidad 02 — Configuración, properties y perfiles

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

## Qué aprenderás
Externalizar valores, crear configuración tipada y separar secretos del repositorio.

# 1. Código vs configuración

No recompiles solo para cambiar puerto, URL externa, timeout, límites o credenciales.

```properties
server.port=8080
app.nombre=Mi API
```

# 2. Fuentes

Spring Boot puede obtener propiedades de archivos, variables de entorno, argumentos y otras fuentes con reglas de precedencia.

No necesitas memorizar toda la jerarquía al principio. Aprende a diagnosticar **qué valor efectivo** recibió la aplicación.

# 3. Variables de entorno

```properties
spring.datasource.password=${DB_PASSWORD}
```

No escribas la contraseña real en Git.

Una variable de entorno tampoco es mágicamente segura: el entorno debe gestionarla apropiadamente.

# 4. Configuración tipada

Para varios valores relacionados:

```java
@ConfigurationProperties(prefix = "app")
public record AppProperties(
    String nombre,
    int limite
) {}
```

Esto facilita validación, navegación y pruebas frente a decenas de `@Value` dispersos.

# 5. Perfiles

```text
application.properties
application-dev.properties
application-test.properties
```

Un perfil puede activar configuración específica.

No conviertas `application-prod.properties` en un almacén de secretos versionados.

# 6. Mismo artefacto, distinto entorno

```text
mismo JAR
├── desarrollo → configuración local
├── test → configuración controlada
└── producción → servicios reales
```

# 7. Defaults

```properties
app.timeout=${APP_TIMEOUT:2s}
```

Un default puede ser útil. No lo uses para ocultar la ausencia de un secreto/valor que debería impedir iniciar.

# 8. Fallar temprano

Si una propiedad esencial falta o es inválida, descubrirlo al arrancar suele ser mejor que en la primera petición crítica.

ConfigurationProperties puede validarse.

# 9. Práctica guiada

Crea:
```text
app.nombre
app.limite
app.modo
```

Mapéalas a configuración tipada y sobrescribe valores en pruebas.

# 10. Errores frecuentes
- secretos en properties versionados;
- @Value por todas partes;
- perfiles como sustituto de diseño;
- no saber el valor efectivo;
- defaults silenciosos para credenciales.

# 11. Reto
Configuración tipada/validada que cambie entre ejecución y test sin modificar código.

# 12. Autoevaluación
1. ¿Por qué externalizar?
2. ¿Cuándo ConfigurationProperties?
3. ¿Perfil debe contener secretos?
4. ¿Por qué fallar temprano?
5. ¿Puede el mismo JAR usar configuraciones distintas?

# 13. Checklist
- [ ] Externalizo.
- [ ] Tipifico configuración.
- [ ] No versiono secretos.
- [ ] Compruebo valores efectivos.

Continúa con inyección.


---

## Continuar el curso

- **Unidad anterior:** [Unidad 01 — Spring, Spring Boot y contenedor IoC](../unidad01-spring-ioc/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 03 — Inyección de dependencias y componentes](../unidad03-dependencias/README.md)
