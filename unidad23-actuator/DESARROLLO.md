# Distinguir salud agregada, proceso vivo y disponibilidad para tráfico

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [ejemplos/api-productos/src/main/resources/application.properties](../ejemplos/api-productos/src/main/resources/application.properties) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

Actuator expone los endpoints seleccionados por configuración. La lectura pública de health oculta components para no publicar detalles internos. Métricas requieren una autoridad de operación; que el proceso pueda responder salud no significa que cualquier cliente deba leer su instrumentación.

Liveness expresa si el proceso se considera vivo y readiness si está listo para recibir tráfico según los componentes del grupo. La referencia incluye db en readiness. Una caída de base puede hacer que ese grupo no esté listo mientras el proceso sigue vivo.

El proveedor de cotizaciones no se agrega automáticamente a liveness. Hacer que una dependencia opcional dispare reinicios de todas las réplicas puede ampliar un problema de disponibilidad. La decisión debe corresponder a qué operaciones pueden continuar de forma útil.

UP no certifica cada regla de negocio ni demuestra que una creación con determinado payload vaya a tener éxito. Los contratos funcionales y las señales operativas se comprueban con casos diferentes.

## 3. Contrasta el comportamiento

Consulta health y sus grupos, y compara con metrics sin identidad y con editor. Explica el resultado esperado de una base caída sin modificar una base personal. Mantén separado el estado agregado de los grupos concretos.

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

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2023%20%E2%80%94%20Actuator%20y%20health%20checks%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad23-actuator%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
