# Distinguir validación de entrada y reglas con estado

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [laboratorios/rest-memoria/src/main/java/com/lelyliliana/rest/ProductoDto.java](../laboratorios/rest-memoria/src/main/java/com/lelyliliana/rest/ProductoDto.java) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

@Valid en el parámetro @RequestBody solicita validar el DTO después de deserializarlo. @Digits limita precisión de precio; @NotBlank y @Pattern establecen reglas de texto. Un JSON que no puede interpretarse falla en otra etapa anterior a evaluar el objeto.

precio 1.001 viola los decimales admitidos y produce 400. Un SKU repetido puede cumplir todas las anotaciones y aun así fallar por el catálogo existente con 409. Una categoría positiva pero ausente produce 404 solo en el proyecto JPA, donde esa relación se modela.

No basta escribir anotaciones en una clase si la frontera no las activa. Tampoco desaparecen las reglas del dominio porque un controller valide: el servicio o constructor puede ser invocado por otro camino. La referencia de memoria supone entradas ya validadas por MVC para sus llamadas HTTP.

Una prueba web de la integrada debe superar autenticación y CSRF para demostrar la validación del cuerpo. Si el filtro rechaza antes, un 403 no constituye evidencia de que @Digits haya funcionado.

## 3. Contrasta el comportamiento

En REST envía 1.001, después un SKU repetido con precio válido. Comprueba 400 y 409 y que ninguno aumenta el total. En JPA usa categoriaId inexistente con formato válido y observa 404. Relaciona cada respuesta con la frontera que realmente la produjo.

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

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2008%20%E2%80%94%20Validaci%C3%B3n%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad08-validacion%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
