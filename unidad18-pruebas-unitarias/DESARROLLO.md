# Probar reglas sin atribuir al mock garantías de la base

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [ejemplos/api-productos/src/test/java/com/lelyliliana/productos/ProductoServiceTest.java](../ejemplos/api-productos/src/test/java/com/lelyliliana/productos/ProductoServiceTest.java) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

La prueba del servicio sustituye repositorios y conserva entidades y valores reales. Puede controlar si un SKU existe o si una categoría está ausente y verificar que una operación rechazada no intenta guardar. El foco es la política de coordinación del servicio.

Los valores de dinero y objetos simples no necesitan convertirse en mocks para este objetivo. Construir Producto real permite detectar invariantes que un objeto falso no ejecutaría, por ejemplo precio no positivo o cantidad de decimales fuera del contrato.

El resultado esperado del servicio es un valor o una excepción de dominio. El status HTTP se decide en la capa web. Afirmar 404 dentro de una prueba que nunca ejecutó MVC confundiría dos fronteras.

Un repositorio simulado que devuelve una entidad no demuestra una restricción UNIQUE, una FK ni un commit real. Esos riesgos requieren pruebas de repositorio e integración. La variedad de pruebas se explica por las preguntas que responde cada una, no por una obligación de simular todos los objetos.

## 3. Contrasta el comportamiento

Ejecuta el caso válido de dominio y los precios rechazados. Relaciona una expectativa de no guardar con la regla que la ocasiona. Después localiza dónde otra suite comprueba UNIQUE real y traducción HTTP.

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

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2018%20%E2%80%94%20Pruebas%20unitarias%20de%20servicios%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad18-pruebas-unitarias%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
