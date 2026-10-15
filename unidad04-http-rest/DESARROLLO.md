# Definir identidad y errores antes de añadir persistencia

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [laboratorios/rest-memoria/src/main/java/com/lelyliliana/rest/ProductoService.java](../laboratorios/rest-memoria/src/main/java/com/lelyliliana/rest/ProductoService.java) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

La etapa REST utiliza un mapa de una JVM y empieza vacía. crear valida unicidad del SKU, asigna un id y conserva ProductoResponse. La petición no incluye categoría, versión ni credenciales. Esas capacidades aparecen en etapas posteriores.

El contrato distingue creación 201, consulta 200, ausencia 404 y conflicto 409. Repetir un SKU válido no es un problema de sintaxis JSON: la petición se entiende, pero el estado actual impide aceptarla. Un id que no existe tampoco es una colección vacía cuando se consulta un recurso individual.

Los métodos synchronized protegen la comprobación y escritura sobre el mismo servicio en esta JVM. Al pasar a una base compartida, el monitor no coordina procesos distintos; por eso JPA incorpora UNIQUE. No describas esa restricción SQL como si ya estuviera ejecutándose en la etapa de mapa.

La API puede devolver status y cuerpo consistentes sin guardar nada en disco. Reiniciar la etapa REST elimina los registros. Persistencia y semántica HTTP se estudian como decisiones relacionadas, pero independientes.

## 3. Contrasta el comportamiento

Crea un producto desde un catálogo vacío, repite el SKU y consulta un id ausente. Compara status, code y cantidad final de registros. Reinicia la aplicación y verifica que el mapa vuelve a estar vacío.

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

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2004%20%E2%80%94%20HTTP%20y%20dise%C3%B1o%20REST%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad04-http-rest%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
