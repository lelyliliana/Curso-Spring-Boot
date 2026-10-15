# Soluciones razonadas 19: Probar la frontera MVC con un doble del servicio

**Proyecto de esta unidad:** [ejemplos/api-productos](../ejemplos/api-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

WebMvcTest carga una slice web. MockitoBean sustituye ProductoService; SecurityConfig se importa para probar permisos reales de esa frontera. Las nuevas anotaciones MVC de Boot 4 viven en org.springframework.boot.webmvc.test.autoconfigure. Comprueba JSON, Location, status, validación y fallos antes del controller. MockMvc no abre un socket de servidor ni prueba JPA por esa prueba de slice.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `ProductoControllerTest` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Explica por qué un POST inválido con autenticación/CSRF ausentes no sirve para probar @Valid.

**Solución y justificación:** El POST inválido destinado a probar @Valid debe incluir autenticación/autoridad correcta y CSRF. Así llega a MVC y obtiene400 por validación del cuerpo. Sin esas condiciones puede recibir 401/403 antes del controller y no demostrar nada sobre @Digits. Las pruebas de filtros y las de validación se comparan como casos distintos.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

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

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa integrado`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
