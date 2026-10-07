# Unidad 23: Actuator y health checks

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

## Qué aprenderás
Exponer información operativa mínima, distinguir liveness/readiness y evitar publicar detalles sensibles.

# 1. Actuator

Añade endpoints operativos como:
- health;
- info;
- metrics;
según dependencias/configuración.

No son endpoints de negocio.

# 2. Health

```text
/actuator/health
```

“UP” no significa que cada caso de negocio funcione.

Un health check debe responder una pregunta operativa concreta.

# 3. Liveness vs readiness

**Liveness:** ¿el proceso debería reiniciarse?  
**Readiness:** ¿puede recibir tráfico útil ahora?

Si una dependencia externa opcional falla, quizá no deba marcar liveness DOWN.

Diseña semántica según arquitectura.

# 4. Dependencias

Base de datos puede ser crítica para readiness.

Un proveedor externo quizá tenga fallback y no deba sacar toda la app del balanceador.

No agregues cada dependencia a health sin analizar efecto.

# 5. Exposición

No publiques todos los endpoints Actuator a Internet por defecto.

Algunos pueden revelar:
- configuración;
- beans;
- entorno;
- métricas internas.

Limita exposición y protege acceso.

# 6. Info

Puede mostrar versión/build cuando sea útil, evitando secretos.

# 7. Custom HealthIndicator

Puedes crear indicador propio si existe una condición operativa que realmente lo requiere.

No conviertas health en una suite de pruebas completa.

# 8. Práctica guiada

Configura health/info y responde:
- ¿qué expones?
- ¿a quién?
- ¿qué hace que readiness falle?
- ¿qué hace que liveness falle?

# 9. Errores frecuentes
- todos los endpoints expuestos;
- health que llama diez servicios lentos;
- liveness dependiente de servicio externo;
- secretos en info;
- UP = negocio perfecto.

# 10. Reto
Diseña política de salud para API con PostgreSQL y proveedor externo.

# 11. Autoevaluación
1. ¿Actuator es API de negocio?
2. ¿Liveness/readiness?
3. ¿UP garantiza todo?
4. ¿Por qué limitar exposición?
5. ¿Toda dependencia debe tumbar health?

# 12. Checklist
- [ ] Expongo mínimo.
- [ ] Distingo salud.
- [ ] Protejo endpoints.
- [ ] Diseño semántica.

Continúa con métricas.


---

## Caso desarrollado: Separar salud del proceso y disponibilidad de dependencias

health público no revela components. Liveness no se acopla al proveedor opcional; readiness incluye db además de readinessState. Actuator expone solo health,info,metrics y protege los no públicos con OPS_READ. UP no prueba cada regla del negocio. Sacar todas las réplicas de tráfico por un proveedor opcional caído puede empeorar una degradación.

### Ejecutar y comprender

1. Prepara el [entorno de tu sistema](../docs/ENTORNO.md).
2. Sigue el [laboratorio completo](LABORATORIO.md), que identifica código, prueba y resultado.
3. Ejecuta desde la raíz:

```text
mvn -f ejemplos/api-productos/pom.xml "-Dtest=FlujoIntegrationTest#saludPublicaYMetricasProtegidas" test
```

4. Resuelve la [práctica](PRACTICA.md).
5. Compara después con las [soluciones razonadas](SOLUCIONES.md).

### Reto explicado

Explica qué cambia si la DB falla y el proceso sigue vivo.

El objetivo es justificar una decisión con evidencia. No necesitas memorizar todas las anotaciones del proyecto avanzado para estudiar esta unidad.

## Continuar el curso

- **Unidad anterior:** [Unidad 22: Seguridad básica con Spring Security](../unidad22-seguridad/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 24: Métricas y observabilidad](../unidad24-metricas/README.md)
