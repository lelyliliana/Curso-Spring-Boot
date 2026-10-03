# Unidad 29 — Taller integrador de APIs

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

## Propósito

Resolver problemas sin que el enunciado indique qué anotación o capa usar.

Debes diseñar el contrato y justificar la arquitectura.

# Método

Para cada problema:
1. contrato HTTP;
2. modelo/reglas;
3. persistencia;
4. errores;
5. seguridad;
6. pruebas;
7. observabilidad;
8. rendimiento cuando aplique;
9. configuración reproducible.

# Nivel 1 — CRUD

API de recursos con:
- creación;
- consulta;
- actualización;
- eliminación;
- validación.

Define status y DTO antes del controller.

# Nivel 2 — Consulta

Búsqueda paginada con filtros.

Decide:
- Page/Slice;
- orden estable;
- query;
- índice.

Observa SQL.

# Nivel 3 — Relaciones

Pedido/Detalle.

Justifica:
- FK;
- navegación JPA;
- cascada;
- transacción;
- DTO.

# Nivel 4 — Integración externa

Consulta proveedor con:
- timeout;
- 404;
- 429;
- 500;
- respuesta inválida.

Diseña política antes de usar librería de resiliencia.

# Nivel 5 — Seguridad

Lectura pública y escritura protegida.

Entrega matriz:
```text
endpoint | identidad | authority
```

Prueba anónimo, autenticado y prohibido.

# Nivel 6 — Pruebas

Para el mismo caso construye:
- unitaria service;
- slice web;
- repository;
- integración.

Explica qué riesgo cubre cada una.

# Nivel 7 — Observabilidad

Define:
- health;
- 5 métricas;
- logs;
- correlation id.

Revisa cardinalidad y datos sensibles.

# Nivel 8 — Rendimiento

Diseña carga gradual y correlaciona con SQL/métricas.

No se acepta “mejoró” sin baseline comparable.

# Nivel 9 — Producción

Empaqueta mismo JAR para dos configuraciones.

Después crea imagen Docker sin secretos.

# Autoevaluación

1. ¿Diseño HTTP antes de anotaciones?
2. ¿Puedo explicar SQL de JPA?
3. ¿Sé elegir alcance de test?
4. ¿Distingo authn/authz?
5. ¿Sé cuándo retry es peligroso?
6. ¿Puedo detectar tag de alta cardinalidad?
7. ¿Puedo ejecutar mismo artefacto en otro entorno?

# Checklist

- [ ] Contrato.
- [ ] Arquitectura.
- [ ] Persistencia.
- [ ] Pruebas.
- [ ] Seguridad.
- [ ] Observabilidad.
- [ ] Reproducibilidad.

Continúa con proyecto final.


---

## Continuar el curso

- **Unidad anterior:** [Unidad 28 — Docker para una API Spring Boot](../unidad28-docker/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 30 — Proyecto final](../unidad30-proyecto-final/README.md)
