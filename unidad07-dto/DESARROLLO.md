# Leer el contrato del DTO de la etapa que estás ejecutando

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [laboratorios/rest-memoria/src/main/java/com/lelyliliana/rest/ProductoDto.java](../laboratorios/rest-memoria/src/main/java/com/lelyliliana/rest/ProductoDto.java) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

ProductoRequest define sku,nombre,precio con restricciones. ProductoResponse añade el id asignado. Son records de entrada/salida; no son beans de servicio. En REST el servicio conserva respuestas en un mapa y no existe una entidad JPA que el controller esté serializando.

Separar contratos ya resulta útil: el cliente no elige id, y la salida puede evolucionar con una decisión explícita. En la etapa JPA, el mapper transforma Producto y su categoría en una respuesta plana, dentro del límite transaccional que permite consultar esos datos.

Al llegar a JPA aparecen categoriaId,categoria,version y activo en la respuesta. Esa diferencia debe leerse en el DTO del proyecto correcto. Copiar el JSON de la etapa integrada no significa que las etapas anteriores ya implementen esos campos.

Un DTO ayuda a evitar que proxies, relaciones o campos internos se conviertan accidentalmente en API. No sustituye autorización ni validación. Un record tipa la forma; las restricciones y el punto donde se ejecutan definen qué entradas se aceptan.

## 3. Contrasta el comportamiento

Compara ProductoRequest/Response de REST con los de JPA. Haz una tabla de campos de entrada y salida, quién asigna el id y cuándo entra la versión. Observa que el nombre DTO no implica anotarlo con @Component.

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

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2007%20%E2%80%94%20DTO%20y%20mapeo%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad07-dto%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
