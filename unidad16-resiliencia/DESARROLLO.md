# Limitar intentos sin confundirlos con un plazo total

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [ejemplos/api-productos/src/main/java/com/lelyliliana/productos/ProveedorClient.java](../ejemplos/api-productos/src/main/java/com/lelyliliana/productos/ProveedorClient.java) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

La configuración exige entre uno y dos intentos y timeouts positivos hasta diez segundos. La conexión y la lectura tienen fronteras distintas. Multiplicar un timeout por el número de intentos puede orientar una estimación, pero no demuestra un deadline total de todo el camino de aplicación.

El bucle solo reintenta respuestas 502,503,504 cuando queda otro intento. Un fallo de transporte termina en indisponibilidad según esta implementación; no todos los errores reciben la misma política. Tampoco hay backoff, jitter ni circuit breaker escondidos en el nombre resiliencia.

La operación es una lectura GET. Copiar el bucle a un comando con efectos podría duplicar una acción si el servidor la ejecutó y la respuesta no llegó. La seguridad de reintentar depende del contrato de operación, no solo del tipo de excepción observado por el cliente.

Si un caso de uso necesitara idempotencia, tendría que definir identidad del comando, almacenamiento y respuesta ante repetición, además de límites temporales. Esa ampliación no está implementada por añadir un header sin un servidor que lo interprete.

## 3. Contrasta el comportamiento

Compara transitorioAcotado, falloPermanente y rateLimitNoReintenta. Cuenta llamadas y predice cuáles hacen una o dos. Describe qué garantía faltaría para trasladar la misma estrategia a una operación de escritura con efectos.

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

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2016%20%E2%80%94%20Timeouts%2C%20errores%20y%20resiliencia%20b%C3%A1sica%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad16-resiliencia%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
