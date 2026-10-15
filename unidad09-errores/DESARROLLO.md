# Traducir errores sin convertir diagnóstico interno en contrato

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [laboratorios/rest-memoria/src/main/java/com/lelyliliana/rest/ApiErrorHandler.java](../laboratorios/rest-memoria/src/main/java/com/lelyliliana/rest/ApiErrorHandler.java) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

El advice recibe excepciones esperadas y crea ProblemDetail con status, detalle y una extensión code. El conflicto de SKU se transforma en 409/CONFLICT; la ausencia en 404/NOT_FOUND. El cliente puede decidir qué corregir sin recibir una consulta SQL ni un stack trace.

Los errores de validación añaden campos desde BindingResult. Se usa un mapa con una incidencia por campo para una representación estable. No es correcto asumir que cualquier fallo HTTP tendrá exactamente la misma lista de campos: un JSON mal formado o una ruta incorrecta tiene otro origen.

En la integrada, los filtros de seguridad pueden responder antes de entrar al advice MVC. Por eso la consistencia del contrato también se diseña en esos handlers. Añadir un catch genérico en cada controller duplicaría políticas y podría transformar fallos diferentes en respuestas exitosas.

El identificador de petición se introduce en otra etapa y ayuda a correlacionar registros. No autentica al emisor ni reemplaza el mensaje útil. Mantén explícita la separación entre lo que necesita el cliente y lo que un operador investiga.

## 3. Contrasta el comportamiento

Provoca un duplicado y revisa Content-Type application/problem+json, status 409 y code CONFLICT. Comprueba ausencia de trace. Después envía un DTO inválido y localiza campos, sin exigir que un 404 de recurso use el mismo contenido de validación.

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

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2009%20%E2%80%94%20Manejo%20global%20de%20errores%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad09-errores%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
