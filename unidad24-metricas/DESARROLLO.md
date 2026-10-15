# Diseñar métricas con un número acotado de combinaciones

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [ejemplos/api-productos/src/main/resources/application.properties](../ejemplos/api-productos/src/main/resources/application.properties) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

Micrometer integra medidas del servidor, JVM y otras piezas. Un timer resume cantidad y duración; un gauge representa un valor observado como conexiones activas. Leer un número sin su unidad, intervalo y etiquetas no basta para interpretar comportamiento.

http.server.requests utiliza rutas normalizadas para no crear una serie diferente por cada id del catálogo. Añadir SKU,correo o request id como tag puede hacer crecer las combinaciones con cada usuario o petición. La etiqueta debe responder una pregunta de operación y tener un conjunto controlado de valores.

El ejercicio pide proponer cinco métricas. La duración del proveedor requiere instrumentación adicional si quieres una medida específica de esa frontera; no se afirma que exista un timer propio solo porque RestClient haga una petición. La referencia proporciona las métricas configuradas y ejemplos para analizarlas.

Una estimación de cardinalidad debe declarar supuestos. Cuatro métodos, diez rutas plantilla y cinco clases de resultado permiten hasta 200 combinaciones por instancia si se usan exactamente esos tags. Histogramas u otras dimensiones pueden multiplicar series; esa cuenta no es una medición real de esta aplicación.

## 3. Contrasta el comportamiento

Propón tasa HTTP, errores, duración HTTP, conexiones activas y duración del proveedor. Indica instrumento, unidad y tags de cada una. Consulta metrics como editor y distingue métricas presentes de las que tu propuesta exigiría implementar.

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

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2024%20%E2%80%94%20M%C3%A9tricas%20y%20observabilidad%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad24-metricas%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
