# Unidad 00 — Entorno y primer proyecto

## Objetivo
Crear, ejecutar y comprobar una aplicación Spring Boot con Java 21 y Maven.

## Requisitos
```bash
java --version
mvn --version
```

## Dependencias iniciales
- Spring Web;
- Validation;
- Spring Boot Test.

## Estructura
```text
src/main/java
src/main/resources
src/test/java
pom.xml
```

## Ejecutar
```bash
mvn spring-boot:run
```

## Empaquetar
```bash
mvn test
mvn package
```

## Diagnóstico
Distingue:
- error Maven/dependencias;
- compilación Java;
- fallo al iniciar contexto;
- puerto ocupado;
- error durante una petición.

## Reto
Crea una aplicación mínima, cambia el puerto mediante configuración y demuestra que las pruebas siguen ejecutándose.
