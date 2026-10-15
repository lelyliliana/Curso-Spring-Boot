# Separar configuración válida, valor por defecto y error de arranque

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [laboratorios/fundamentos/src/main/java/com/lelyliliana/fundamentos/SaludoProperties.java](../laboratorios/fundamentos/src/main/java/com/lelyliliana/fundamentos/SaludoProperties.java) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

@ConfigurationProperties enlaza el prefijo saludo con un record tipado. @EnableConfigurationProperties registra esa pieza y @Validated activa sus restricciones. Ser un record no registra automáticamente cualquier clase como configuración de la aplicación.

El prefijo no puede estar vacío y longitudMaxima debe estar entre 1 y 100. El archivo de propiedades proporciona Hola y 40. Sobrescribir longitudMaxima con 0 debe impedir un contexto válido; no se utiliza el valor anterior para ocultar la configuración incorrecta.

Para un argumento con espacios conserva el argumento completo entre comillas en la terminal. Sin ese cuidado podrías enviar Buen como valor y día como otro argumento. En un proceso iniciado desde Python, la lista de argumentos ya mantiene cada elemento como una unidad.

Un perfil selecciona configuración. No vuelve secretos los valores que escribas en un archivo de Git ni cambia por sí mismo el contrato del DTO. Las claves de la API integrada se suministran externamente al llegar a esa etapa.

## 3. Contrasta el comportamiento

Arranca el JAR con "--saludo.prefijo=Buen día" y comprueba el mensaje. En otra ejecución usa --saludo.longitud-maxima=0 y registra que falla al iniciar. No intentes probar una petición exitosa sobre un proceso que rechazó la configuración.

## 4. Repite un recorrido ejecutable

Después de construir el JAR de esta etapa según [LABORATORIO.md](LABORATORIO.md), ejecuta:

```text
python scripts/recorridos.py --etapa fundamentos
```

El programa arranca su propio proceso local con puerto temporal y datos ficticios, comprueba resultados y lo detiene al terminar. Observa los casos de esta unidad y relaciona cada expectativa con el archivo real. Para temas de diseño o medición, el recorrido funcional complementa la actividad; no sustituye una migración, benchmark o prueba de infraestructura que no se ejecutó.

[Intentar la práctica](PRACTICA.md) · [Contrastar la solución](SOLUCIONES.md)

---

### ¿Necesitas apoyo con este tema?

Si te bloqueaste con la práctica, necesitas revisar tu entorno o adaptar lo aprendido a tu proyecto, podemos trabajarlo en una sesión personalizada.

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2002%20%E2%80%94%20Configuraci%C3%B3n%2C%20properties%20y%20perfiles%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad02-configuracion%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
