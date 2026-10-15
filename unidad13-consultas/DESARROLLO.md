# Conservar un orden reproducible al paginar

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [laboratorios/jpa-productos/src/main/java/com/lelyliliana/productos/ProductoRepository.java](../laboratorios/jpa-productos/src/main/java/com/lelyliliana/productos/ProductoRepository.java) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

El repositorio deriva la consulta por nombre y recibe Pageable. El servicio crea PageRequest con orden por id. Sin un orden estable, comparar páginas repetidas o escribir pruebas sobre el primer resultado sería ambiguo aunque la consulta devolviera registros válidos.

El nombre de un producto no tiene que ser único. Por eso un orden solo por nombre podría necesitar un desempate por id; en esta referencia id ya ofrece una política inequívoca para un estado de datos dado. Inserciones o eliminaciones entre consultas todavía pueden desplazar páginas basadas en offset.

Pagina separa contenido, número, tamaño, total y cantidad de páginas. total proviene de la consulta de todas las coincidencias y no se deduce del tamaño de la página actual. Una página vacía no obliga a que total sea cero.

EntityGraph carga categoría para el mapeo requerido. No transforma cualquier consulta con relaciones en una operación óptima ni elimina necesariamente la consulta de conteo de Page. Lee SQL y mide un caso concreto antes de afirmar que todo ocurrió en una consulta.

## 3. Contrasta el comportamiento

Crea registros con nombres coincidentes y consulta dos páginas manteniendo el mismo filtro. Compara id, total y contenido. Observa consultas y evita mezclar inserciones concurrentes con una expectativa de páginas congeladas que el contrato no ofrece.

## 4. Repite un recorrido ejecutable

Después de construir el JAR de esta etapa según [LABORATORIO.md](LABORATORIO.md), ejecuta:

```text
python scripts/recorridos.py --etapa jpa
```

El programa arranca su propio proceso local con puerto temporal y datos ficticios, comprueba resultados y lo detiene al terminar. Observa los casos de esta unidad y relaciona cada expectativa con el archivo real. Para temas de diseño o medición, el recorrido funcional complementa la actividad; no sustituye una migración, benchmark o prueba de infraestructura que no se ejecutó.

[Intentar la práctica](PRACTICA.md) · [Contrastar la solución](SOLUCIONES.md)

---

### ¿Necesitas apoyo con este tema?

Si te bloqueaste con la práctica, necesitas revisar tu entorno o adaptar lo aprendido a tu proyecto, podemos trabajarlo en una sesión personalizada.

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2013%20%E2%80%94%20Consultas%20y%20paginaci%C3%B3n%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad13-consultas%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
