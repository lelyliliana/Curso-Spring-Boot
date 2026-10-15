# Distinguir objetos administrados y resultados por petición

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [laboratorios/fundamentos/src/main/java/com/lelyliliana/fundamentos/SaludoService.java](../laboratorios/fundamentos/src/main/java/com/lelyliliana/fundamentos/SaludoService.java) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

FundamentosApplication declara Clock como bean. SaludoService se descubre por @Service y recibe Clock y SaludoProperties por constructor. SaludoController recibe el servicio. El grafo de dependencias se puede leer sin buscar manualmente objetos dentro de un registro global.

El servicio administra lógica reutilizable y no guarda un nombre de usuario como campo mutable. Cada llamada recibe su nombre y crea un Saludo distinto. Si el servicio singleton conservara temporalmente esos datos como estado compartido, solicitudes diferentes podrían interferir.

Saludo es un record de resultado y no un componente del contenedor. Sus valores corresponden a una operación, no a dependencias que Spring deba resolver al iniciar. Anotarlo como @Component no es necesario para que el conversor HTTP lo represente como JSON.

Singleton aquí se refiere a una definición de bean dentro de un contexto. No significa una sola instancia universal para todas las JVM, pruebas o aplicaciones. La identidad del contenedor y el contrato de ausencia de estado por petición son preguntas distintas.

## 3. Contrasta el comportamiento

Dibuja las dependencias del controller al servicio y del servicio a reloj/configuración. Ejecuta ContenedorTest y compara con SaludoServiceTest: la primera comprueba el ensamblaje; la segunda construye objetos sin Spring para probar la lógica.

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

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2001%20%E2%80%94%20Spring%2C%20Spring%20Boot%20y%20contenedor%20IoC%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad01-spring-ioc%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
