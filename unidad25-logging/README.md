# Unidad 25 — Logging y trazabilidad

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

## Qué aprenderás
Registrar eventos con contexto útil, correlacionar solicitudes y proteger información sensible.

# 1. Log no es println

Spring Boot usa infraestructura de logging configurable.

Ventajas:
- niveles;
- formato;
- destinos;
- contexto;
- integración operativa.

# 2. Niveles

ERROR: fallo significativo.  
WARN: condición anómala/recuperable.  
INFO: evento operacional útil.  
DEBUG/TRACE: diagnóstico detallado.

No conviertas cada excepción esperada en ERROR.

# 3. Logging estructurado

En producción, campos estructurados facilitan consulta:
```text
event=pedido_creado pedidoId=42 resultado=ok
```

Evita concatenar datos sin contexto.

# 4. Correlation ID

Un identificador por solicitud puede propagarse mediante MDC/contexto y aparecer en logs.

No uses como tag de métrica de alta cardinalidad.

# 5. Qué registrar

Ejemplo integración externa:
- proveedor;
- operación;
- duración;
- status/categoría de error;
- correlation id.

No necesitas registrar cuerpo completo.

# 6. Nunca

- password;
- token;
- Authorization;
- cookies sensibles;
- datos personales innecesarios.

Enmascarar parcialmente puede ser necesario según dato/política.

# 7. Stack traces

En error inesperado, registra excepción con causa en servidor.

No envíes stack trace al cliente.

# 8. Duplicación

Si registras la misma excepción en cada capa, puedes generar tres stack traces por un fallo.

Decide dónde existe suficiente contexto para registrar.

# 9. Práctica guiada

Flujo crear pedido + proveedor externo.

Diseña logs:
- inicio relevante;
- resultado;
- fallo;
- contexto.

Revisa cada campo por sensibilidad/cardinalidad.

# 10. Errores frecuentes
- todo ERROR;
- bodies completos;
- secretos;
- excepción logueada en cada capa;
- logs sin correlation;
- usar logs como única métrica.

# 11. Reto
Estrategia de logs para un flujo con fallo externo y error interno.

# 12. Autoevaluación
1. ¿INFO/DEBUG?
2. ¿Qué es MDC/correlation?
3. ¿Qué no registrar?
4. ¿Stack trace al cliente?
5. ¿Por qué evitar log duplicado?

# 13. Checklist
- [ ] Niveles con intención.
- [ ] Contexto útil.
- [ ] Protejo datos.
- [ ] Correlaciono.

Continúa con rendimiento.


---

## Continuar el curso

- **Unidad anterior:** [Unidad 24 — Métricas y observabilidad](../unidad24-metricas/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 26 — Rendimiento y pruebas de carga](../unidad26-rendimiento/README.md)
