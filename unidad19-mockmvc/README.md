# Unidad 19: Pruebas web con MockMvc

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

## Qué aprenderás
Probar contrato MVC: rutas, serialización, validación, status, headers y manejo de errores.

# 1. Qué estamos probando

MockMvc ejecuta infraestructura Spring MVC sin necesitar normalmente un servidor TCP real.

Queremos comprobar:
```text
HTTP simulado → controller → JSON/status
```

# 2. WebMvcTest

```java
@WebMvcTest(ProductoController.class)
class ProductoControllerTest { ... }
```

Carga una porción web, no toda la aplicación.

Las dependencias del controller deben proporcionarse/mokearse según la versión/configuración de Spring Boot usada.

# 3. GET

```java
mockMvc.perform(get("/api/productos/1"))
    .andExpect(status().isOk())
    .andExpect(content().contentTypeCompatibleWith(
        MediaType.APPLICATION_JSON))
    .andExpect(jsonPath("$.id").value(1));
```

# 4. POST

Envía:
- Content-Type;
- JSON;
- espera 201;
- Location;
- cuerpo.

No pruebes solo status si el contrato incluye más.

# 5. Validación

Request inválido:
```text
precio = -1
```

debe activar contrato de error esperado.

Prueba campos/código estable, no textos internos frágiles cuando no forman parte del contrato.

# 6. Advice

Incluye/configura el `@RestControllerAdvice` apropiado para probar 404/409/validación.

Así pruebas traducción de excepciones.

# 7. Service mock

En una slice web, service puede ser doble.

No estás probando JPA aquí.

# 8. Seguridad

Cuando llegue Spring Security, pruebas web deberán incluir contexto/autenticación apropiados. Un 401/403 puede ocurrir antes del controller.

# 9. Práctica guiada

Prueba:
- GET existente;
- GET inexistente;
- POST válido;
- POST inválido;
- conflicto.

# 10. Errores frecuentes
- probar solo 200;
- levantar todo contexto;
- JSON completo rígido cuando solo importan campos;
- olvidar headers;
- creer que MockMvc prueba BD.

# 11. Reto
Suite del contrato HTTP que detecte cambios incompatibles.

# 12. Autoevaluación
1. ¿MockMvc abre servidor real?
2. ¿Qué prueba WebMvcTest?
3. ¿Service real necesariamente?
4. ¿Qué probar además de status?
5. ¿MockMvc prueba JPA?

# 13. Checklist
- [ ] Pruebo rutas/status.
- [ ] Pruebo JSON/headers.
- [ ] Pruebo validación/errores.
- [ ] Mantengo alcance web.

Continúa con JPA tests.


---

## Caso desarrollado: Probar la frontera MVC con un doble del servicio

WebMvcTest carga una slice web. MockitoBean sustituye ProductoService; SecurityConfig se importa para probar permisos reales de esa frontera. Las nuevas anotaciones MVC de Boot 4 viven en org.springframework.boot.webmvc.test.autoconfigure. Comprueba JSON, Location, status, validación y fallos antes del controller. MockMvc no abre un socket de servidor ni prueba JPA por esa prueba de slice.

### Ejecutar y comprender

1. Prepara el [entorno de tu sistema](../docs/ENTORNO.md).
2. Sigue el [laboratorio completo](LABORATORIO.md), que identifica código, prueba y resultado.
3. Ejecuta desde la raíz:

```text
mvn -f ejemplos/api-productos/pom.xml "-Dtest=ProductoControllerTest" test
```

4. Resuelve la [práctica](PRACTICA.md).
5. Compara después con las [soluciones razonadas](SOLUCIONES.md).

### Reto explicado

Explica por qué un POST inválido con autenticación/CSRF ausentes no sirve para probar @Valid.

El objetivo es justificar una decisión con evidencia. No necesitas memorizar todas las anotaciones del proyecto avanzado para estudiar esta unidad.

## Continuar el curso

- **Unidad anterior:** [Unidad 18: Pruebas unitarias de servicios](../unidad18-pruebas-unitarias/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 20: Pruebas de persistencia JPA](../unidad20-pruebas-jpa/README.md)
