# Unidad 28: Docker para una API Spring Boot

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

**Proyecto de esta unidad:** [ejemplos/api-productos](../ejemplos/api-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Qué aprenderás
Construir una imagen reproducible, entender imagen/contenedor/red y pasar configuración sin incrustar secretos.

# 1. Imagen vs contenedor

**Imagen:** artefacto inmutable por capas.  
**Contenedor:** instancia en ejecución de una imagen.

Un contenedor Linux comparte el kernel Linux del entorno donde se ejecuta. En Ubuntu puede ser el del host; Docker Desktop en Windows/macOS utiliza un entorno Linux virtualizado.

# 2. Dockerfile simple

```dockerfile
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/app.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
```

En un proyecto real fija una etiqueta/digest apropiado y actualiza deliberadamente.

# 3. Build

```bash
docker build -t mi-api .
```

# 4. Run

```bash
docker run --rm -p 8080:8080 mi-api
```

`-p host:contenedor`.

# 5. localhost

Dentro del contenedor:
```text
localhost = ese contenedor
```

Si PostgreSQL está en otro contenedor, no uses localhost para referenciarlo; usa red/nombre de servicio apropiado.

# 6. Configuración

```bash
docker run -e DB_URL=... mi-api
```

No pongas secretos reales en Dockerfile ni los hornees en la imagen.

El mecanismo concreto de secretos depende de plataforma.

# 7. Multi-stage

Puedes compilar dentro de una etapa Maven/JDK y copiar solo JAR a imagen runtime.

También puedes construir fuera y copiar artefacto.

Elige según pipeline, caché y reproducibilidad.

# 8. Usuario

Evita ejecutar como root cuando la imagen/aplicación lo permita; configura usuario no privilegiado.

# 9. Healthcheck

Puede complementar Actuator.

No confundas health del contenedor con todas las garantías del negocio.

# 10. Persistencia

El filesystem writable del contenedor es efímero por naturaleza operacional.

No guardes datos importantes localmente sin volumen/servicio externo apropiado.

# 11. Práctica guiada

1. package;
2. build imagen;
3. run;
4. configura puerto/DB;
5. conecta PostgreSQL externo/controlado;
6. comprueba health;
7. detén/recrea.

# 12. Errores frecuentes
- DB en localhost incorrecto;
- secreto en imagen;
- latest sin control;
- root innecesario;
- datos importantes en filesystem efímero;
- Docker como sustituto de pruebas.

# 13. Reto
Imagen reproducible que reciba toda configuración por entorno y no contenga credenciales.

# 14. Autoevaluación
1. ¿Imagen/contenedor?
2. ¿localhost dentro?
3. ¿Qué hace -p?
4. ¿Secretos en Dockerfile?
5. ¿Por qué usuario no root?
6. ¿Contenedor garantiza persistencia?

# 15. Checklist
- [ ] Construyo imagen.
- [ ] Comprendo red.
- [ ] Externalizo config.
- [ ] No incrusto secretos.

Continúa con taller.


---

## Caso desarrollado: Construir imagen y comprender red y persistencia

Dockerfile copia el JAR y ejecuta UID 10001. La imagen base tiene una etiqueta de Java 21 y se debe actualizar deliberadamente o fijar digest en un release; etiqueta no garantiza los mismos bytes para siempre. localhost dentro del contenedor es ese contenedor. En Docker Desktop los contenedores Linux usan un kernel Linux de su entorno virtual, no el kernel Windows/macOS directamente.

### Ejecutar y comprender

1. Prepara el [entorno de tu sistema](../docs/ENTORNO.md).
2. Sigue el [laboratorio completo](LABORATORIO.md), que identifica código, prueba y resultado.
3. Ejecuta desde la raíz:

```text
mvn -f ejemplos/api-productos/pom.xml "-Dtest=FlujoIntegrationTest" test
```

4. Resuelve la [práctica](PRACTICA.md).
5. Compara después con las [soluciones razonadas](SOLUCIONES.md).

### Reto explicado

Conecta PostgreSQL como servicio db y evita exponer la DB a Internet.

El objetivo es justificar una decisión con evidencia. No necesitas memorizar todas las anotaciones del proyecto avanzado para estudiar esta unidad.

## Continuar el curso

- **Unidad anterior:** [Unidad 27: Empaquetado y configuración de producción](../unidad27-produccion/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 29: Taller integrador de APIs](../unidad29-taller/README.md)

---

### ¿Necesitas apoyo con este tema?

Si te bloqueaste con la práctica, necesitas revisar tu entorno o adaptar lo aprendido a tu proyecto, podemos trabajarlo en una sesión personalizada.

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2028%20%E2%80%94%20Docker%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad28-docker%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
