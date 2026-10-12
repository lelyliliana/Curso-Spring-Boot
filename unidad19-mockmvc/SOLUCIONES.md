# Soluciones razonadas 19: Probar la frontera MVC con un doble del servicio

**Proyecto de esta unidad:** [ejemplos/api-productos](../ejemplos/api-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

WebMvcTest carga una slice web. MockitoBean sustituye ProductoService; SecurityConfig se importa para probar permisos reales de esa frontera. Las nuevas anotaciones MVC de Boot 4 viven en org.springframework.boot.webmvc.test.autoconfigure. Comprueba JSON, Location, status, validación y fallos antes del controller. MockMvc no abre un socket de servidor ni prueba JPA por esa prueba de slice.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `ProductoControllerTest` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Explica por qué un POST inválido con autenticación/CSRF ausentes no sirve para probar @Valid.

**Solución y justificación:** Los filtros pueden rechazarlo antes de deserializar. postInvalido aporta identidad con authority y token, así llega a MVC y verifica campos del error. Otra prueba se ocupa del rechazo de permisos.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Aislar la validación MVC

En ProductoControllerTest#postInvalido observa:
```java
.with(user("editor").authorities(() -> "PRODUCT_WRITE"))
.with(csrf())
```
Solo después de cumplir permiso y CSRF el cuerpo inválido llega a @Valid. Espera 400 y campos.precio; comprueba que service.crear no se invocó. Sin token, el rechazo 403 ocurre antes de ese controller; sin identidad pero con token, 401. La prueba correcta demuestra la frontera que pretende evaluar.
