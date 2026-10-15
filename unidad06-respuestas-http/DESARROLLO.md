# Comprobar una creación por sus tres partes

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [laboratorios/rest-memoria/src/main/java/com/lelyliliana/rest/ProductoController.java](../laboratorios/rest-memoria/src/main/java/com/lelyliliana/rest/ProductoController.java) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

ResponseEntity.created recibe la URI del nuevo recurso. El framework representa el record como JSON y prepara una respuesta con status 201 y Location. Mirar solo el cuerpo no comprueba que el contrato de creación esté completo.

El id lo genera el servicio y el cliente debe leerlo del resultado o seguir Location. No fijes /5 por haberlo visto en una prueba con un servicio sustituido. Los ids de una ejecución pueden depender de operaciones previas y, en una base, las secuencias pueden dejar huecos.

GET de Location demuestra que el recurso se puede consultar. DELETE devuelve 204 sin cuerpo. Un cliente que intenta analizar JSON obligatorio en una respuesta vacía introduce un error propio aunque el servidor haya cumplido su contrato.

ProductoResponse de REST contiene id,sku,nombre,precio. No hay version en esta etapa. JPA añade ese campo para control de cambios y debe comprobarse al estudiar su proyecto, no exigirse retroactivamente al mapa.

## 3. Contrasta el comportamiento

Crea un producto, guarda Location y Content-Type y consulta la ruta devuelta. Elimina y comprueba ausencia de cuerpo junto al 204. Consulta de nuevo y espera 404. No presupongas un id fijo ni una versión en REST.

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

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2006%20%E2%80%94%20Request%2C%20ResponseEntity%20y%20c%C3%B3digos%20HTTP%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad06-respuestas-http%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
