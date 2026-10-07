# Laboratorio 02: Sobrescribir una propiedad sin recompilar

## Preparar

Sigue [ENTORNO.md](../docs/ENTORNO.md). Esta práctica usa [laboratorios/fundamentos](../laboratorios/fundamentos/README.md). Los comandos parten de la raíz del repositorio; no necesitas mover archivos a una ruta personal ni cambiar paquetes Java.

## Observar una decisión concreta

SaludoProperties es un record @ConfigurationProperties validado; @EnableConfigurationProperties lo registra. application.properties fija Hola y máximo 40; la prueba sobrescribe el prefijo por Bienvenida. Una propiedad tipada no se registra automáticamente por ser record. En la API las contraseñas esenciales deben existir y los timeouts se validan al arrancar. Un perfil activa configuración, no almacena secretos de forma segura.

## Paso 1: predecir

Escribe la regla central, el dato o contrato que participa y qué resultado distinguiría una solución correcta de otra equivocada. Usa un caso normal y una frontera. Evita anotar «funciona» sin decir qué esperas observar.

## Paso 2: leer la implementación

Abre el proyecto indicado y localiza controller, servicio, configuración o prueba según el tema. Identifica sus dependencias y qué partes son infraestructura compartida. El ejemplo completo contiene temas posteriores; concéntrate ahora en la frontera de esta unidad. Las clases tienen imports y pertenencia a paquetes; los fragmentos de teoría son ilustraciones, no archivos Java sueltos para compilar.

### Archivos que debes localizar

- [application.properties](../laboratorios/fundamentos/src/main/resources/application.properties)

Anota qué método o propiedad realiza la decisión descrita y qué parte depende del framework. Sigue su recorrido desde entrada hasta resultado; identifica un rechazo antes de ejecutar.

## Paso 3: ejecutar la prueba

```text
mvn -f laboratorios/fundamentos/pom.xml "-Dtest=ContenedorTest#configuracionExterna" test
```

En Windows usa PowerShell; en Ubuntu/macOS usa terminal. Mantén comillas en -Dtest si incluye # para que se envíe como un solo argumento. Maven debe terminar con BUILD SUCCESS y cero fallos. La prueba comprueba la decisión descrita, no todas las capacidades de la aplicación. Lee qué dobles, base o contexto usa antes de atribuirle otra garantía.

## Paso 4: contrastar

Compara con la explicación del caso. Cuando hay HTTP, usa también [CONTRATO_API.md](../docs/CONTRATO_API.md) y el cliente de práctica; cuando hay persistencia, identifica la migración y el SQL. Los errores de permisos o entorno no se corrigen eliminando validación o desactivando controles.

## Paso 5: aplicar

Arranca el saludo con --saludo.prefijo=Buen día y prueba un máximo de nombre inválido.

Entrega predicción, comando o cambio, resultado y explicación. Resuelve [PRACTICA.md](PRACTICA.md) antes de abrir [SOLUCIONES.md](SOLUCIONES.md).

[Unidad](README.md) · [Comprobación completa](../docs/VERIFICACION.md)
