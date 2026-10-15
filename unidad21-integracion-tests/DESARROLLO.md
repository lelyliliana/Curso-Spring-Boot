# Comprobar el dato posterior a una operación rechazada

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [ejemplos/api-productos/src/test/java/com/lelyliliana/productos/FlujoIntegrationTest.java](../ejemplos/api-productos/src/test/java/com/lelyliliana/productos/FlujoIntegrationTest.java) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

La integración conecta MVC, servicios, repositorios y migraciones. MockMvc puede recorrer esas capas dentro del contexto sin abrir un socket de servidor. La comprobación adicional del JAR sí cruza HTTP/TCP real y observa la aplicación empaquetada.

El caso de versión antigua necesita una secuencia: crear con versión 0, actualizar válidamente y recibir 1, intentar otra actualización con 0 y comprobar 409. La consulta posterior debe conservar el precio confirmado. Mirar solo el error no demuestra que no hubo una escritura parcial.

La comparación explícita del número recibido permite rechazar una intención vieja. @Version añade defensa frente a una carrera posterior a esa lectura. Son controles relacionados; ninguno debe describirse como un bloqueo global que impide toda concurrencia.

Un @Transactional en el test puede revertir trabajo del mismo hilo y ocultar la diferencia entre flush y commit. Una petición a un servidor real no se revierte porque el cliente de prueba tenga esa anotación. Por eso los recorridos operativos usan procesos y bases de práctica aislados.

## 3. Contrasta el comportamiento

Sigue la secuencia 0→1→rechazo 0 y vuelve a consultar. Registra precio y versión después del conflicto. Contrasta el mecanismo de aislamiento de la suite MockMvc con el proceso real que arranca scripts/verificar.py.

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

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2021%20%E2%80%94%20Pruebas%20de%20integraci%C3%B3n%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad21-integracion-tests%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
