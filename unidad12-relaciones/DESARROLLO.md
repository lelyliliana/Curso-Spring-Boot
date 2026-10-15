# Relacionar propiedad del modelo y borrado de datos compartidos

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [laboratorios/jpa-productos/src/main/java/com/lelyliliana/productos/Producto.java](../laboratorios/jpa-productos/src/main/java/com/lelyliliana/productos/Producto.java) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

Producto contiene una relación ManyToOne hacia Categoria y es dueño de la FK categoria_id. Una categoría puede compartirse entre productos; eliminar uno no debe eliminar esa categoría. No se añade CascadeType.REMOVE de forma indiscriminada para hacer desaparecer un error de persistencia.

LAZY describe una estrategia de carga y no garantiza una cantidad universal de consultas. El repositorio utiliza EntityGraph para traer categoría en recorridos que necesitan sus datos. La respuesta DTO se construye dentro del contexto apropiado en lugar de convertir un proxy en un contrato HTTP accidental.

El taller añade otra relación: Pedido posee sus detalles y los productos son compartidos. En JPA la cascada PERSIST guarda los nuevos detalles; en SQL ON DELETE CASCADE elimina detalles cuando se elimina un pedido. Son mecanismos en capas distintas y no órdenes de borrar el producto referenciado.

orphanRemoval tiene sentido respecto al ciclo de vida del detalle dentro del pedido. No demuestra que cualquier eliminación futura sea válida en el negocio. Antes de elegir una cascada dibuja quién puede existir de forma independiente y qué historial debe conservarse.

## 3. Contrasta el comportamiento

Distingue producto/categoría de pedido/detalle. Ejecuta la prueba que elimina un pedido y comprueba que desaparecen sus detalles pero el producto sigue disponible. Contrasta con una FK que impide borrar una categoría con productos.

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

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2012%20%E2%80%94%20Entidades%20y%20relaciones%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad12-relaciones%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
