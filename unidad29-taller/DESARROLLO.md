# Entregar un recorrido reconstruible de la API

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [docs/CONTRATO_API.md](../docs/CONTRATO_API.md) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

El taller requiere que otra persona reconstruya el resultado a partir de rutas del repositorio, requisitos y comandos. Una captura de una petición exitosa no explica qué proyecto arrancaste, qué datos existían ni qué política de autenticación estaba activa.

Elige la etapa integrada después de comprender las tres anteriores. Lee contrato, migraciones, permisos y tests. Conserva un caso válido y un rechazo por frontera: entrada, estado, persistencia y acceso. La evidencia de un mock y la de un JAR real deben nombrarse de manera distinta.

Los recorridos guiados crean datos ficticios dentro de procesos locales aislados. Muestran status esperado y comprueban datos posteriores, evitando depender del id de una ejecución manual previa. El propósito es hacer visible la secuencia que otro estudiante puede repetir.

La entrega también documenta límites: no hay pagos ni inventario físico, las cuentas son educativas y el proveedor se prueba localmente. Agregar clases o anotaciones sin una responsabilidad no hace más completa la solución de un problema distinto.

## 3. Contrasta el comportamiento

Pide a otra persona que siga solo tu README y registre un caso de creación, conflicto, versión vieja, CSRF y consulta protegida. Si necesita una ruta personal o un secreto incrustado, revisa la entrega. Usa los doce recorridos como comparación de alcance y evidencia.

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

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2029%20%E2%80%94%20Taller%20de%20APIs%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad29-taller%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
