# Entender qué significa localhost dentro de cada contenedor

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [ejemplos/api-productos/compose.yml](../ejemplos/api-productos/compose.yml) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

Compose declara db y api en una red de proyecto. Desde api el nombre db resuelve el servicio de PostgreSQL y la URL es jdbc:postgresql://db:5432/productos. localhost dentro de ese contenedor no señala automáticamente la base de otro servicio ni el equipo anfitrión.

La base usa un volumen para sus datos y no declara ports hacia el host. La API sí publica 8080 enlazado a 127.0.0.1 del anfitrión. Exponer un puerto en Dockerfile mediante EXPOSE no equivale a publicarlo; la política de acceso depende de configuración de ejecución.

Dockerfile copia el JAR construido y ejecuta con un usuario numérico sin privilegios. La etiqueta de la imagen base puede cambiar de bytes con el tiempo; fijar y actualizar un digest pertenece a una política de entrega, no se obtiene solo por escribir Java 21.

La prueba Docker del workflow utiliza red host en Linux para su entorno controlado. Ese comando no se traslada idénticamente a cualquier instalación de escritorio. La práctica multiplataforma usa Compose y debe comprobar red, base, migraciones y API en su entorno real.

## 3. Contrasta el comportamiento

Lee DB_URL y ports antes de arrancar. Identifica qué servicio necesita un volumen y qué credenciales recibe externamente. Distingue construir la imagen de demostrar que el contenedor conecta y conserva datos. No publiques la base para resolver un error de nombre interno.

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

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2028%20%E2%80%94%20Docker%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad28-docker%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
