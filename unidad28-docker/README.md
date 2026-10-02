# Unidad 28 — Docker para una API Spring Boot

## Qué aprenderás
Construir una imagen reproducible, entender imagen/contenedor/red y pasar configuración sin incrustar secretos.

# 1. Imagen vs contenedor

**Imagen:** artefacto inmutable por capas.  
**Contenedor:** instancia en ejecución de una imagen.

Docker no es una máquina virtual completa; comparte kernel del host bajo su modelo de aislamiento.

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
