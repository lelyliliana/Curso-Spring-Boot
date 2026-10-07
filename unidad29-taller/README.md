# Unidad 29: Taller integrador de APIs

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

# Nivel 1: CRUD

API de recursos con:
- creación;
- consulta;
- actualización;
- eliminación;
- validación.

Define status y DTO antes del controller.

# Nivel 2: Consulta

Búsqueda paginada con filtros.

Decide:
- Page/Slice;
- orden estable;
- query;
- índice.

Observa SQL.

# Nivel 3: Relaciones

Pedido/Detalle.

Justifica:
- FK;
- navegación JPA;
- cascada;
- transacción;
- DTO.

# Nivel 4: Integración externa

Consulta proveedor con:
- timeout;
- 404;
- 429;
- 500;
- respuesta inválida.

Diseña política antes de usar librería de resiliencia.

# Nivel 5: Seguridad

Lectura pública y escritura protegida.

Entrega matriz:
```text
endpoint | identidad | authority
```

Prueba anónimo, autenticado y prohibido.

# Nivel 6: Pruebas

Para el mismo caso construye:
- unitaria service;
- slice web;
- repository;
- integración.

Explica qué riesgo cubre cada una.

# Nivel 7: Observabilidad

Define:
- health;
- 5 métricas;
- logs;
- correlation id.

Revisa cardinalidad y datos sensibles.

# Nivel 8: Rendimiento

Diseña carga gradual y correlaciona con SQL/métricas.

No se acepta “mejoró” sin baseline comparable.

# Nivel 9: Producción

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

## Caso desarrollado: Integrar contrato, persistencia, seguridad y operación

Resuelve primero PROBLEMAS.md. La referencia existente demuestra CRUD y actualización con versión, filtro paginado, categoría relacionada, migrations, proveedor con fallos, seguridad y Actuator. No basta que una ruta responda 200: deben coincidir los límites del contrato, las garantías de datos y los permisos. Conserva evidencia de un caso válido y un fallo por frontera.

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

Entrega una solución de taller que otro equipo reconstruya sin preguntarte rutas personales.

El objetivo es justificar una decisión con evidencia. No necesitas memorizar todas las anotaciones del proyecto avanzado para estudiar esta unidad.

## Continuar el curso

- **Unidad anterior:** [Unidad 28: Docker para una API Spring Boot](../unidad28-docker/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 30: Proyecto final](../unidad30-proyecto-final/README.md)
