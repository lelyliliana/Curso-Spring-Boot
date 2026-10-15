# Seguir el arranque hasta una respuesta HTTP

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [laboratorios/fundamentos/src/main/java/com/lelyliliana/fundamentos/FundamentosApplication.java](../laboratorios/fundamentos/src/main/java/com/lelyliliana/fundamentos/FundamentosApplication.java) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

main entrega la clase principal a SpringApplication.run. El proceso crea el contexto, descubre componentes y arranca el servidor embebido. El saludo no aparece por ejecutar javac sobre un archivo aislado: necesita las dependencias y la configuración del proyecto Maven.

Fundamentos se limita a MVC, validación y pruebas. La ruta /api/saludos llama al servicio y devuelve un record con mensaje y fecha. No requiere una base de datos ni las cuentas editor/lector de una etapa posterior. Mantener ese alcance permite investigar un arranque sin mezclar errores de JPA o seguridad.

La prueba de puerto reutiliza el mismo JAR. El argumento --server.port=8081 cambia configuración de esa ejecución y no modifica el código. Un puerto ocupado es un fallo de arranque; un 404 corresponde a una petición que sí recibió respuesta del servidor. Tampoco abrir otra ruta en el navegador hace que cambie el puerto de la aplicación.

Trabaja desde la raíz cuando uses los comandos que incluyen -f o la ruta del JAR. En este repositorio no hay pom.xml en la raíz; Maven necesita el del proyecto de fundamentos.

## 3. Contrasta el comportamiento

Construye fundamentos según LABORATORIO.md y arranca su JAR con --server.port=8081. Consulta el saludo en ese puerto y guarda status y cuerpo. Después prueba una ruta inexistente en el mismo origen: el 404 no significa que el proceso haya dejado de escuchar.

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

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2000%20%E2%80%94%20Entorno%20y%20primer%20proyecto%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad00-entorno%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
