# Unidad 04: HTTP y diseño de APIs REST

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

## Qué aprenderás
Diseñar contratos HTTP antes de controllers: recursos, métodos, códigos, headers e idempotencia.

# 1. HTTP primero

Una API Spring Boot sigue siendo HTTP.

Antes de escribir `@GetMapping`, comprende:

```text
GET /api/productos/42
```

# 2. Petición y respuesta

Petición:
```text
método + URI + headers + cuerpo opcional
```

Respuesta:
```text
status + headers + cuerpo opcional
```

# 3. Recursos

Preferimos:

```text
/api/productos
/api/productos/42
```

frente a rutas basadas siempre en verbos como `/obtenerProductos`.

Es una convención de diseño, no una ley matemática.

# 4. Métodos

**GET:** obtener; semánticamente seguro e idempotente.  
**POST:** crear/procesar; no se asume idempotente.  
**PUT:** actualización/reemplazo bajo contrato; idempotente.  
**PATCH:** cambio parcial; idempotencia depende de operación/formato.  
**DELETE:** efecto deseado idempotente aunque respuestas repetidas puedan diferir.

# 5. Idempotencia

Repetir una operación idempotente produce el mismo efecto deseado sobre el estado que hacerla una vez.

No significa respuesta idéntica byte a byte.

# 6. Códigos

- 200 OK;
- 201 Created;
- 204 No Content;
- 400 Bad Request;
- 401 Unauthorized: falta/falla autenticación;
- 403 Forbidden: no autorizado para esa acción según política;
- 404 Not Found;
- 409 Conflict;
- 500 Internal Server Error.

El código depende del resultado real y contrato.

# 7. Creación

```text
POST /api/productos
→ 201 Created
Location: /api/productos/42
```

# 8. PUT vs PATCH

Define si PUT exige representación completa y cómo PATCH representa cambios.

No implementes ambos como “actualizar lo que venga” sin contrato.

# 9. Query params

```text
GET /api/productos?categoria=libros&page=0&size=20
```

Apropiados para filtros/paginación.

# 10. JSON

JSON es una **representación del contrato**, no necesariamente tu entidad de persistencia.

# 11. Práctica guiada

Diseña una API de tareas sin Spring.

Para cada operación documenta:

```text
método | URI | request | status | response | errores
```

Incluye listar, consultar, crear, cambiar estado y eliminar.

# 12. Errores frecuentes
- 200 para todo;
- GET que modifica;
- PUT/PATCH indistintos;
- status por costumbre;
- diseñar desde entidad JPA.

# 13. Reto
Contrato completo de tareas antes de escribir anotaciones.

# 14. Autoevaluación
1. ¿Qué compone petición?
2. ¿Qué es idempotencia?
3. ¿201?
4. ¿401/403?
5. ¿PUT/PATCH?
6. ¿JSON = entidad JPA?

# 15. Checklist
- [ ] Diseño recursos.
- [ ] Elijo método/status.
- [ ] Documento errores.
- [ ] Comprendo HTTP sin Spring.

Continúa con controllers.


---

## Caso desarrollado: Diseñar una creación antes de escribir anotaciones

POST /api/productos crea un recurso con 201 y Location. GET no pide crear ni modificar recursos. PUT reemplaza los campos del contrato de actualización y exige la versión leída; se utiliza 409 si esa versión está desactualizada. DELETE exitoso devuelve 204 sin cuerpo; repetirlo puede devolver 404 y seguir siendo idempotente en el efecto (el recurso permanece ausente). REST no equivale a cualquier JSON sobre HTTP: incluye restricciones arquitectónicas además de convenciones de rutas.

### Ejecutar y comprender

1. Prepara el [entorno de tu sistema](../docs/ENTORNO.md).
2. Sigue el [laboratorio completo](LABORATORIO.md), que identifica código, prueba y resultado.
3. Ejecuta desde la raíz:

```text
mvn -f ejemplos/api-productos/pom.xml "-Dtest=ProductoControllerTest#creacionContrato" test
```

4. Resuelve la [práctica](PRACTICA.md).
5. Compara después con las [soluciones razonadas](SOLUCIONES.md).

### Reto explicado

Define contrato para un SKU duplicado y un id ausente.

El objetivo es justificar una decisión con evidencia. No necesitas memorizar todas las anotaciones del proyecto avanzado para estudiar esta unidad.

## Continuar el curso

- **Unidad anterior:** [Unidad 03: Inyección de dependencias y componentes](../unidad03-dependencias/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 05: Controllers y endpoints](../unidad05-controllers/README.md)
