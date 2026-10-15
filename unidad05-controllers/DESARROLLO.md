# Separar total de coincidencias y contenido de una página

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [laboratorios/rest-memoria/src/main/java/com/lelyliliana/rest/ProductoController.java](../laboratorios/rest-memoria/src/main/java/com/lelyliliana/rest/ProductoController.java) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

El controller enlaza nombre, pagina y tamano y delega el listado. El servicio filtra sin distinguir mayúsculas con Locale.ROOT, ordena por id y luego aplica desplazamiento y límite. El campo total corresponde a todas las coincidencias, no solo a las filas de la página.

La etapa de memoria no carga productos de demostración al arrancar. Crea Bases de datos con un SKU y Algoritmos con otro antes de esperar una coincidencia. No busques automáticamente LIB-02 por haberlo visto en los datos dev de la API integrada.

Con una coincidencia y tamaño 2, página 0 contiene una fila y páginas=1. Página 1 contiene cero filas y conserva total=1. Esta situación es una página fuera del contenido disponible, no un recurso individual ausente ni un error por sí misma.

Pagina debe ser no negativa y tamano estar entre 1 y 100. El desplazamiento se calcula como long para evitar que el producto de dos int se desborde antes de saltar elementos. Repetir la consulta no modifica la colección.

## 3. Contrasta el comportamiento

Crea ambos productos y consulta nombre=base,pagina=0,tamano=2. Después cambia solo pagina a 1. Compara total, paginas y contenido. Prueba tamano 101 y observa rechazo 400, sin interpretar ese caso como una página vacía válida.

## 4. Repite un recorrido ejecutable

Después de construir el JAR de esta etapa según [LABORATORIO.md](LABORATORIO.md), ejecuta:

```text
python scripts/recorridos.py --etapa rest
```

El programa arranca su propio proceso local con puerto temporal y datos ficticios, comprueba resultados y lo detiene al terminar. Observa los casos de esta unidad y relaciona cada expectativa con el archivo real. Para temas de diseño o medición, el recorrido funcional complementa la actividad; no sustituye una migración, benchmark o prueba de infraestructura que no se ejecutó.

[Intentar la práctica](PRACTICA.md) · [Contrastar la solución](SOLUCIONES.md)

---

### ¿Necesitas apoyo con este tema?

Si te bloqueaste con la práctica, necesitas revisar tu entorno o adaptar lo aprendido a tu proyecto, podemos trabajarlo en una sesión personalizada.

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2005%20%E2%80%94%20Controllers%20y%20endpoints%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad05-controllers%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
