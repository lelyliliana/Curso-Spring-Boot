# Unidad 24 — Métricas y observabilidad

## Qué aprenderás
Definir métricas que respondan preguntas operativas y evitar cardinalidad descontrolada.

# 1. Observabilidad

Logs, métricas y trazas responden preguntas distintas y se complementan.

Métrica:
> ¿cuántas peticiones fallan?

Log:
> ¿qué ocurrió en esta ejecución concreta?

Traza:
> ¿por dónde pasó una solicitud distribuida?

# 2. Micrometer

Spring Boot integra Micrometer como fachada de instrumentación para distintos backends.

No necesitas elegir un backend específico para aprender a diseñar métricas.

# 3. Tipos

**Counter:** eventos acumulativos.  
**Gauge:** valor actual observado.  
**Timer:** duración y conteo.  
**Distribution summary:** distribución de valores.

El backend puede representar/agregar de formas particulares.

# 4. Métrica útil

```text
pedidos_creados_total
```

puede responder volumen.

```text
api_externa_duracion
```

ayuda con latencia.

No midas algo solo porque puedes.

# 5. Tags

Buenos candidatos:
```text
resultado=ok|error
proveedor=clima
```

Peligrosos:
```text
userId=938472
requestId=uuid-unico
email=...
```

Cada valor único puede crear una serie diferente y provocar explosión de cardinalidad.

# 6. Percentiles

Promedio puede esconder colas lentas.

p95/p99 pueden ayudar a entender experiencia de solicitudes más lentas, según configuración/backend.

No confundas percentil con porcentaje de éxito.

# 7. RED

Para servicios:
- Rate;
- Errors;
- Duration.

Es una guía útil, no un checklist universal.

# 8. Práctica guiada

Para endpoint POST /pedidos define:
- tasa;
- errores;
- duración;
- métrica de negocio.

Para cada tag estima cuántos valores distintos puede tener.

# 9. Errores frecuentes
- IDs únicos como tags;
- medir sin pregunta;
- solo promedio;
- nombres ambiguos;
- métricas con datos personales.

# 10. Reto
Cinco métricas con pregunta, tipo, tags y decisión que permiten tomar.

# 11. Autoevaluación
1. ¿Counter/gauge?
2. ¿Qué mide Timer?
3. ¿Qué es cardinalidad?
4. ¿Por qué userId es mal tag?
5. ¿Qué aporta p95?

# 12. Checklist
- [ ] Métricas con propósito.
- [ ] Tags acotados.
- [ ] Evito PII.
- [ ] Interpreto distribuciones.

Continúa con logging.
