# Superar filtros para demostrar validación del cuerpo

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [ejemplos/api-productos/src/test/java/com/lelyliliana/productos/ProductoControllerTest.java](../ejemplos/api-productos/src/test/java/com/lelyliliana/productos/ProductoControllerTest.java) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

@WebMvcTest crea una slice web y MockitoBean sustituye el servicio. SecurityConfig se incorpora para que los filtros mantengan su política en esta frontera. La prueba observa status, headers y JSON sin abrir un puerto TCP de servidor ni ejecutar JPA.

Un POST puede rechazarse por CSRF o autorización antes de que MVC deserialice y valide el DTO. Si quieres probar @Valid, entrega una identidad con PRODUCT_WRITE y un token adecuado. Después un precio inválido debe producir 400 y un error de campos, no un 403 que ocurrió antes.

La matriz de seguridad merece pruebas separadas: identidad ausente, autoridad insuficiente y token ausente son razones diferentes. Cuando esas condiciones se mezclan, el primer control que rechaza oculta qué habrían hecho los siguientes.

Una respuesta 201 de una slice con servicio sustituido prueba la construcción de la respuesta del controller. No demuestra que se haya insertado un producto en una base. La misma afirmación requiere un caso de integración con las piezas reales.

## 3. Contrasta el comportamiento

Lee postInvalido y ubica identidad, authority y csrf. Retira una condición en una copia y observa cómo cambia el origen del rechazo. Restaura y contrasta con el test de integración que guarda y consulta un recurso.

## 4. Repite un recorrido ejecutable

Después de construir el JAR de esta etapa según [LABORATORIO.md](LABORATORIO.md), ejecuta:

```text
python scripts/recorridos.py --etapa integrado
```

El programa arranca su propio proceso local con puerto temporal y datos ficticios, comprueba resultados y lo detiene al terminar. Observa los casos de esta unidad y relaciona cada expectativa con el archivo real. Para temas de diseño o medición, el recorrido funcional complementa la actividad; no sustituye una migración, benchmark o prueba de infraestructura que no se ejecutó.

[Intentar la práctica](PRACTICA.md) · [Contrastar la solución](SOLUCIONES.md)

---

### ¿Necesitas apoyo con este tema?

Si te bloqueaste con la práctica, necesitas revisar tu entorno o adaptar lo aprendido a tu proyecto, podemos trabajarlo en una sesión personalizada.

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2019%20%E2%80%94%20Pruebas%20de%20controllers%20con%20MockMvc%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad19-mockmvc%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
