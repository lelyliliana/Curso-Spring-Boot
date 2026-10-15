# Identificar el control que rechaza cada petición

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [ejemplos/api-productos/src/main/java/com/lelyliliana/productos/SecurityConfig.java](../ejemplos/api-productos/src/main/java/com/lelyliliana/productos/SecurityConfig.java) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

La cadena de filtros se ejecuta antes de muchos caminos MVC. La API permite lectura pública del catálogo y salud; la escritura de /api requiere PRODUCT_WRITE y operación de Actuator exige OPS_READ. Los pedidos también requieren PRODUCT_WRITE aunque su consulta sea GET, porque no forman parte de las rutas públicas declaradas.

Basic identifica usuarios educativos en memoria. En este diseño los navegadores pueden reenviar credenciales, por lo que CSRF sigue activo. El cliente obtiene /api/csrf, conserva la cookie de sesión y envía el header indicado con su token. La identidad y el token son controles distintos.

Para aislar un 401 de identidad, la petición anónima del recorrido lleva token válido. Un lector con token supera esa parte pero no tiene autoridad de escritura y recibe 403. Un editor sin token puede recibir 403 antes de llegar al caso de uso. El editor con ambos controles puede crear 201 si el payload es válido.

No se agrega un CORS permisivo para resolver autenticación ni se desactiva CSRF por ver un rechazo. Las cuentas en memoria y HTTP local pertenecen a la demostración, no a una plataforma de identidad de producción.

## 3. Contrasta el comportamiento

Ejecuta los cuatro casos con el mismo cuerpo y cambia una sola condición. Después prueba la consulta de pedidos con lector. Registra qué control permite avanzar y cuál rechaza sin atribuir todos los 403 a una contraseña incorrecta.

## 4. Repite un recorrido ejecutable

Después de construir el JAR de esta etapa según [LABORATORIO.md](LABORATORIO.md), ejecuta:

```text
python scripts/recorridos.py --etapa integrado
```

El programa arranca su propio proceso local con puerto temporal y datos ficticios, comprueba resultados y lo detiene al terminar. Observa los casos de esta unidad y relaciona cada expectativa con el archivo real. Para temas de diseño o medición, el recorrido funcional complementa la actividad; no sustituye una migración, benchmark o prueba de infraestructura que no se ejecutó.

[Intentar la práctica](PRACTICA.md) · [Contrastar la solución](SOLUCIONES.md)

## Matriz de escritura de la referencia

| Identidad | CSRF | Resultado con cuerpo válido | Frontera |
|---|---|---|---|
| Anónima | Válido | 401 | Identidad requerida |
| Lector | Válido | 403 | Falta PRODUCT_WRITE |
| Editor | Ausente | 403 | Protección CSRF |
| Editor | Válido | 201 | Creación aceptada si cumple reglas |

---

### ¿Necesitas apoyo con este tema?

Si te bloqueaste con la práctica, necesitas revisar tu entorno o adaptar lo aprendido a tu proyecto, podemos trabajarlo en una sesión personalizada.

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2022%20%E2%80%94%20Seguridad%20b%C3%A1sica%20con%20Spring%20Security%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad22-seguridad%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
