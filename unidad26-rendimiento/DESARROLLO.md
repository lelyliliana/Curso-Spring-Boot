# Comparar una hipótesis de consultas con datos y resultados iguales

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [scripts/carga.py](../scripts/carga.py) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

El script de carga limita URL a loopback, cantidad entre 1 y 1000 y concurrencia entre 1 y 16. Realiza calentamiento, mide latencias y cuenta errores. Ese resultado pertenece a un servidor local, población y equipo concretos; no es una promesa de capacidad de producción.

N+1 se investiga observando consultas asociadas al acceso a relaciones. Una página con varias categorías puede mostrar consultas adicionales al mapear la respuesta. EntityGraph expresa una estrategia para traer categoría en esa consulta, pero debe compararse SQL, cantidad de consultas y resultado funcional.

Cambia una sola variable por experimento: estrategia de fetch, índice o tamaño de página. Si cambias varias a la vez, una diferencia de latencia no identifica cuál produjo el efecto. Conserva versión, datos y condiciones de concurrencia.

Con cuatro registros de demostración no se demuestra la utilidad universal de un índice. Los ensayos de rendimiento no sustituyen las pruebas de reglas; la API debe seguir devolviendo los mismos datos, errores y orden después del cambio.

## 3. Contrasta el comportamiento

Toma una línea base, observa SQL y relaciona consultas con acceso a categoría. En una copia modifica solo el fetch, repite la misma población y compara conteo/resultados. Después mide solicitudes acotadas sin cambiar otras condiciones.

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

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2026%20%E2%80%94%20Rendimiento%20y%20pruebas%20de%20carga%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad26-rendimiento%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
