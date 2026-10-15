# Convertir una respuesta externa en un contrato propio

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [ejemplos/api-productos/src/main/java/com/lelyliliana/productos/ProveedorClient.java](../ejemplos/api-productos/src/main/java/com/lelyliliana/productos/ProveedorClient.java) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

ProveedorClient recibe configuración validada y construye un RestClient con fábrica JDK. Antes de llamar valida el SKU para no aceptar una URL arbitraria como identificador. La URL base pertenece a configuración y el segmento del recurso se construye mediante la plantilla del cliente.

Una respuesta 200 aún debe producir una Cotizacion no nula, del SKU esperado y con precio positivo. Un cuerpo vacío, JSON inválido o esquema no aceptable no se presenta como una cotización exitosa. El DTO externo no elimina la obligación de comprobar significado.

El proveedor 404 se traduce a ausencia 404 local. Una respuesta 429 se representa como indisponibilidad 503 en esta política educativa y no se reintenta. Los códigos 502,503,504 pueden reintentarse de forma acotada cuando quedan intentos. No se propaga automáticamente cada detalle técnico del proveedor al consumidor local.

Los tests usan un servidor HTTP local controlado. Así prueban transporte y conversión reales sin depender de una API pública. Una prueba con RestClient sustituido cubriría otro límite y no demostraría que un JSON concreto se analiza correctamente.

## 3. Contrasta el comportamiento

Ejecuta ProveedorClientTest y relaciona 200,404,429,503,JSON inválido y SKU incorrecto con su resultado. Observa el contador de llamadas, no solo la excepción. El recorrido integrado comprueba el 503 HTTP local cuando el proveedor no está disponible.

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

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2015%20%E2%80%94%20Consumo%20de%20APIs%20externas%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad15-apis-externas%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
