# Laboratorio 16: Acotar fallos y reintentos

## Preparar

Sigue [ENTORNO.md](../docs/ENTORNO.md). Esta práctica usa [ejemplos/api-productos](../ejemplos/api-productos/README.md). Los comandos parten de la raíz del repositorio; no necesitas mover archivos a una ruta personal ni cambiar paquetes Java.

## Observar una decisión concreta

Por defecto intentos=1. Para el experimento se admite 2 y se repiten solo respuestas 502/503/504 de este GET. 404, 429, timeout y JSON inválido no se reintentan automáticamente. El ejemplo no implementa circuit breaker ni un presupuesto de tiempo global; dos intentos pueden aumentar la latencia. En un sistema real define backoff/jitter, presupuesto total y política de Retry-After si aplica.

## Paso 1: predecir

Escribe la regla central, el dato o contrato que participa y qué resultado distinguiría una solución correcta de otra equivocada. Usa un caso normal y una frontera. Evita anotar «funciona» sin decir qué esperas observar.

## Paso 2: leer la implementación

Abre el proyecto indicado y localiza controller, servicio, configuración o prueba según el tema. Identifica sus dependencias y qué partes son infraestructura compartida. El ejemplo completo contiene temas posteriores; concéntrate ahora en la frontera de esta unidad. Las clases tienen imports y pertenencia a paquetes; los fragmentos de teoría son ilustraciones, no archivos Java sueltos para compilar.

### Archivos que debes localizar

- [ProveedorClient.java](../ejemplos/api-productos/src/main/java/com/lelyliliana/productos/ProveedorClient.java)
- [AppProperties.java](../ejemplos/api-productos/src/main/java/com/lelyliliana/productos/AppProperties.java)

Anota qué método o propiedad realiza la decisión descrita y qué parte depende del framework. Sigue su recorrido desde entrada hasta resultado; identifica un rechazo antes de ejecutar.

## Paso 3: ejecutar la prueba

```text
mvn -f ejemplos/api-productos/pom.xml "-Dtest=ProveedorClientTest" test
```

En Windows usa PowerShell; en Ubuntu/macOS usa terminal. Mantén comillas en -Dtest si incluye # para que se envíe como un solo argumento. Maven debe terminar con BUILD SUCCESS y cero fallos. La prueba comprueba la decisión descrita, no todas las capacidades de la aplicación. Lee qué dobles, base o contexto usa antes de atribuirle otra garantía.

## Paso 4: contrastar

Compara con la explicación del caso. Cuando hay HTTP, usa también [CONTRATO_API.md](../docs/CONTRATO_API.md) y el cliente de práctica; cuando hay persistencia, identifica la migración y el SQL. Los errores de permisos o entorno no se corrigen eliminando validación o desactivando controles.

## Paso 5: aplicar

Justifica por qué no copiar este retry a un POST de pago.

Entrega predicción, comando o cambio, resultado y explicación. Resuelve [PRACTICA.md](PRACTICA.md) antes de abrir [SOLUCIONES.md](SOLUCIONES.md).

[Unidad](README.md) · [Comprobación completa](../docs/VERIFICACION.md)
