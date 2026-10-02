# Unidad 28 — Docker

## Objetivo
Empaquetar aplicación y runtime de forma reproducible.

## Dockerfile conceptual
```dockerfile
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/app.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
```

Fija una imagen apropiada en un proyecto real y actualízala deliberadamente.

## No copies secretos
Usa configuración externa.

## Health
El healthcheck del contenedor y Actuator pueden complementarse según arquitectura.

## Reto
Construye imagen, pasa configuración por entorno y conecta a una base sin incrustar credenciales.
