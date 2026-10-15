# Delimitar una tarea local y una coordinación entre réplicas

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [ejemplos/api-productos/src/main/java/com/lelyliliana/productos/ResumenJob.java](../ejemplos/api-productos/src/main/java/com/lelyliliana/productos/ResumenJob.java) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

ResumenJob solo se registra cuando app.tareas-habilitadas=true. La configuración normal lo mantiene apagado para que el curso no genere trabajo periódico sin que se estudie esa unidad. El componente recibe el repositorio igual que otra pieza administrada.

fixedDelay programa una espera después de terminar la ejecución previa según el scheduler de esta instancia. No significa una hora universal de inicio ni una exclusión compartida por todas las réplicas de la aplicación. Dos procesos pueden crear dos jobs independientes.

El trabajo del ejemplo cuenta productos y registra un resumen. Su lectura repetida no equivale a emitir dos veces un documento con efectos de negocio. Antes de trasladar el patrón a una operación no repetible, define la identidad del trabajo y dónde se conserva su progreso.

Un bloqueo distribuido requiere un recurso común y una política de vencimiento/recuperación; la idempotencia sigue importando si un trabajador pierde su propiedad después de actuar. No se obtiene coordinación global por poner synchronized o @Scheduled en una clase.

## 3. Contrasta el comportamiento

Activa el job en una copia de práctica y observa su log con un intervalo corto controlado. Después explica qué ocurriría con dos procesos. No utilices ese ensayo como evidencia de una exclusión que el código no implementa.

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

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2017%20%E2%80%94%20Tareas%20programadas%20y%20procesamiento%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad17-tareas%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
