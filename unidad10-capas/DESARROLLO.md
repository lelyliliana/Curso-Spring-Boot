# Asignar una garantía a la capa que puede cumplirla

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [laboratorios/rest-memoria/src/main/java/com/lelyliliana/rest/ProductoService.java](../laboratorios/rest-memoria/src/main/java/com/lelyliliana/rest/ProductoService.java) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

El controller transforma HTTP en una intención, el servicio coordina reglas del mapa, el DTO define datos y el advice traduce errores. Una capa no se justifica solo porque exista una carpeta con ese nombre: sigue una acción para ver qué decisión aporta.

crear comprueba SKU y guarda dentro del mismo monitor synchronized. actualizar excluye el propio id al comprobar SKU, para permitir conservarlo sin acusar un duplicado de sí mismo. Una actualización rechazada no sustituye la respuesta anterior en el mapa.

Al pasar a JPA, existsBySku permite un rechazo temprano, pero dos transacciones pueden consultar antes de insertar. UNIQUE en la base es la garantía frente a esa carrera. El advice traduce el conflicto esperado; no debes afirmar que una consulta previa eliminó toda posibilidad concurrente.

El monitor de la etapa REST protege una instancia en una JVM. No actúa sobre réplicas ni sobre un servidor de base independiente. Esa limitación explica el cambio de arquitectura de la siguiente etapa sin descalificar el ejemplo de memoria para su objetivo.

## 3. Contrasta el comportamiento

En REST intenta actualizar A al SKU que ya pertenece a B y verifica que A conserve su nombre/precio. Dibuja dónde intervienen servicio y advice. En JPA añade a ese mapa la restricción UNIQUE y la transacción.

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

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2010%20%E2%80%94%20Capas%20y%20responsabilidades%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad10-capas%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
