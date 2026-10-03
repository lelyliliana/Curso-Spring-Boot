# Unidad 27 — Empaquetado y configuración de producción

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

## Qué aprenderás
Construir un artefacto reproducible, externalizar configuración y cerrar la aplicación de forma controlada.

# 1. Build

```bash
mvn clean test package
```

`clean` aquí se usa para construir desde salida limpia, no como cura universal.

El pipeline real puede separar fases y usar cachés.

# 2. Artefacto

```text
target/app.jar
```

Debe ser el mismo artefacto promovido entre entornos cuando sea posible.

Evita compilar un JAR distinto para cada contraseña/URL.

# 3. Configuración

Externaliza:
- datasource;
- credenciales;
- endpoints;
- timeouts;
- flags necesarios.

Valida configuración crítica al arrancar.

# 4. Secretos

No:
- Git;
- Dockerfile;
- imagen;
- logs.

En producción usa el mecanismo de secretos de la plataforma/entorno.

# 5. Perfil producción

Puede definir comportamiento no secreto, pero no es una bóveda.

# 6. Graceful shutdown

Al recibir señal de parada:
- deja de aceptar/encaminar nuevo trabajo según plataforma;
- permite terminar solicitudes dentro de límites;
- cierra recursos.

Spring Boot ofrece soporte configurable.

# 7. Readiness

Durante inicio/cierre, readiness puede ayudar al balanceador a no enviar tráfico prematuramente.

# 8. Migraciones

Decide quién ejecuta migraciones:
- aplicación al iniciar;
- job/pipeline previo;
según arquitectura.

Con múltiples réplicas, analiza coordinación.

# 9. Logs

En contenedores/plataformas suele ser útil escribir logs a stdout/stderr y dejar recolección a plataforma, en lugar de archivos locales efímeros.

# 10. Práctica guiada

Ejecuta mismo JAR:
- config A;
- config B;
sin recompilar.

Envía señal de cierre y observa logs.

# 11. Errores frecuentes
- secretos en perfil;
- build distinto por entorno;
- migración no coordinada;
- matar proceso sin cierre;
- archivos de log locales como única estrategia.

# 12. Reto
Checklist de release reproducible desde test hasta ejecución con configuración externa.

# 13. Autoevaluación
1. ¿Mismo artefacto?
2. ¿Perfil = secretos?
3. ¿Qué es graceful shutdown?
4. ¿Readiness en cierre?
5. ¿Quién ejecuta migraciones?

# 14. Checklist
- [ ] Build reproducible.
- [ ] Config externa.
- [ ] Secretos fuera.
- [ ] Cierre controlado.

Continúa con Docker.


---

## Continuar el curso

- **Unidad anterior:** [Unidad 26 — Rendimiento y pruebas de carga](../unidad26-rendimiento/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 28 — Docker para una API Spring Boot](../unidad28-docker/README.md)
