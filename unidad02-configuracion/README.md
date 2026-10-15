# Unidad 02: Configuración, properties y perfiles

**[Desarrollo del código y resolución paso a paso](DESARROLLO.md)**

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

**Proyecto de esta unidad:** [laboratorios/fundamentos](../laboratorios/fundamentos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

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

## Caso desarrollado: Sobrescribir una propiedad sin recompilar

SaludoProperties es un record @ConfigurationProperties validado; @EnableConfigurationProperties lo registra. application.properties fija Hola y máximo 40; la prueba sobrescribe el prefijo por Bienvenida. Una propiedad tipada no se registra automáticamente por ser record. En la API las contraseñas esenciales deben existir y los timeouts se validan al arrancar. Un perfil activa configuración, no almacena secretos de forma segura.

### Ejecutar y comprender

1. Prepara el [entorno de tu sistema](../docs/ENTORNO.md).
2. Sigue el [laboratorio completo](LABORATORIO.md), que identifica código, prueba y resultado.
3. Ejecuta desde la raíz:

```text
mvn -f laboratorios/fundamentos/pom.xml "-Dtest=ContenedorTest#configuracionExterna" test
```

4. Resuelve la [práctica](PRACTICA.md).
5. Compara después con las [soluciones razonadas](SOLUCIONES.md).

### Reto explicado

Arranca el saludo con --saludo.prefijo=Buen día y prueba un máximo de nombre inválido.

El objetivo es justificar una decisión con evidencia. No necesitas memorizar todas las anotaciones del proyecto avanzado para estudiar esta unidad.

## Continuar el curso

- **Unidad anterior:** [Unidad 01: Spring, Spring Boot y contenedor IoC](../unidad01-spring-ioc/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 03: Inyección de dependencias y componentes](../unidad03-dependencias/README.md)

---

### ¿Necesitas apoyo con este tema?

Si te bloqueaste con la práctica, necesitas revisar tu entorno o adaptar lo aprendido a tu proyecto, podemos trabajarlo en una sesión personalizada.

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2002%20%E2%80%94%20Configuraci%C3%B3n%2C%20properties%20y%20perfiles%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad02-configuracion%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
