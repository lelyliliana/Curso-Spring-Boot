# Separar persistir, enviar SQL y confirmar una transacción

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [laboratorios/jpa-productos/src/main/java/com/lelyliliana/productos/ProductoService.java](../laboratorios/jpa-productos/src/main/java/com/lelyliliana/productos/ProductoService.java) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

La etapa JPA conserva el contrato REST y añade entidad, repositorio, categoría y versión. El servicio se obtiene del contenedor y sus métodos de escritura están delimitados por @Transactional. El controller solicita un caso de uso sin administrar directamente EntityManager.

saveAndFlush fuerza sincronización SQL dentro de la transacción. Permite observar ciertas restricciones antes de construir la respuesta, pero flush no equivale a commit. El proxy transaccional completa la unidad cuando la llamada de servicio retorna correctamente; todavía pueden aparecer fallos de confirmación.

La entidad leída dentro de una transacción queda administrada. actualizar cambia sus campos, flush sincroniza y @Version participa en la detección de conflictos. No hace falta interpretar cada cambio como otro objeto que se debe serializar directamente hacia HTTP.

Una llamada interna de un método a otro no atraviesa necesariamente el proxy transaccional habitual. Tampoco @Transactional(readOnly=true) es un permiso de seguridad que vuelva imposible toda escritura. Identifica qué objeto recibe la llamada y qué frontera activa la infraestructura.

## 3. Contrasta el comportamiento

Sigue crear desde controller a servicio y repositorio. Localiza saveAndFlush y distingue qué error puede aparecer ahí y qué implica la confirmación. Ejecuta una prueba real de restricción y contrástala con una prueba de servicio que usa repositorios Mockito.

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

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2011%20%E2%80%94%20Spring%20Data%20JPA%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad11-jpa%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
