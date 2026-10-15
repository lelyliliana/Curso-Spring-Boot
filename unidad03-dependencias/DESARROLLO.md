# Probar una dependencia sin abrir el servidor

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [laboratorios/fundamentos/src/main/java/com/lelyliliana/fundamentos/SaludoService.java](../laboratorios/fundamentos/src/main/java/com/lelyliliana/fundamentos/SaludoService.java) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

El constructor hace explícitas las dos dependencias del servicio: Clock y SaludoProperties. La prueba puede entregarlas con new. La lógica de responder no depende de que exista un socket HTTP ni necesita buscar beans mediante ApplicationContext.

Clock.fixed fija instante y zona para obtener una fecha repetible. Así el test no cambia de resultado al llegar medianoche. La configuración de prueba conserva el máximo del nombre y un prefijo conocido, de modo que la respuesta se deriva de entradas controladas.

responder rechaza null, blanco y nombres cuya longitud recortada supera el máximo. Después de validar crea un Saludo con nombre normalizado. Un caso de 40 unidades UTF-16 se acepta y uno de 41 se rechaza con máximo 40. Esta medida no equivale siempre al número de grafemas visibles.

Un único constructor no necesita @Autowired. Si añades dos beans del mismo tipo sin una selección, aparece una ambigüedad de ensamblaje que esta prueba unitaria no pretende cubrir. Distingue la corrección de la regla de la configuración del contenedor.

## 3. Contrasta el comportamiento

Ejecuta solo SaludoServiceTest desde el pom de fundamentos. Identifica el Clock fijo, construye un nombre de 40 y otro de 41 unidades y relaciona cada expectativa con la condición del servicio. Prueba espacios exteriores y vacío.

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

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2003%20%E2%80%94%20Inyecci%C3%B3n%20de%20dependencias%20y%20componentes%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad03-dependencias%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
