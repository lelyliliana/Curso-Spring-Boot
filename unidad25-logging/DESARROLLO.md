# Conservar correlación sin confiar ciegamente en un header

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [ejemplos/api-productos/src/main/java/com/lelyliliana/productos/RequestIdFilter.java](../ejemplos/api-productos/src/main/java/com/lelyliliana/productos/RequestIdFilter.java) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

El filtro acepta X-Request-Id solo si tiene entre 1 y 64 caracteres alfanuméricos o guion. Si no cumple, genera UUID. El valor elegido se devuelve como header y se incorpora al MDC para los registros del hilo que procesa la petición.

El bloque finally registra método/status y elimina la entrada del MDC. Limpiar importa cuando el contenedor reutiliza hilos: un request posterior no debe heredar el identificador anterior por un campo que quedó sin retirar.

El identificador no autentica al cliente ni prueba que una petición venga de un sistema de confianza. Sirve para relacionar eventos. El filtro no registra Authorization, cookies o cuerpos completos para demostrar que la solicitud existe.

La propagación a tareas asíncronas o llamadas externas es otra responsabilidad. MDC basado en el contexto del hilo no se transporta automáticamente a cualquier ejecución. Un test del header tampoco demuestra por sí solo un sistema distribuido de trazas.

## 3. Contrasta el comportamiento

Envía guia-123 y verifica eco del header. Después usa 80 caracteres y espera un UUID nuevo que cumpla el formato. No introduzcas saltos de línea reales en una herramienta HTTP que los rechace antes de llegar al servidor y lo presentes como una prueba del filtro.

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

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2025%20%E2%80%94%20Logging%20y%20trazabilidad%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad25-logging%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
