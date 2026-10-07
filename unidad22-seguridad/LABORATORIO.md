# Laboratorio 22: Probar identidad, autoridad y CSRF por separado

## Preparar

Sigue [ENTORNO.md](../docs/ENTORNO.md). Esta práctica usa [ejemplos/api-productos](../ejemplos/api-productos/README.md). Los comandos parten de la raíz del repositorio; no necesitas mover archivos a una ruta personal ni cambiar paquetes Java.

## Observar una decisión concreta

GET del catálogo es público. Escritura exige PRODUCT_WRITE; métricas OPS_READ. Basic es educativo y necesita TLS fuera del entorno local. CSRF permanece activo porque los navegadores pueden reenviar credenciales Basic automáticamente. /api/csrf genera token ligado a sesión; el cliente conserva cookie y envía el header indicado. Un anónimo con token válido recibe 401; una petición sin CSRF puede recibir 403 antes de autenticación.

## Paso 1: predecir

Escribe la regla central, el dato o contrato que participa y qué resultado distinguiría una solución correcta de otra equivocada. Usa un caso normal y una frontera. Evita anotar «funciona» sin decir qué esperas observar.

## Paso 2: leer la implementación

Abre el proyecto indicado y localiza controller, servicio, configuración o prueba según el tema. Identifica sus dependencias y qué partes son infraestructura compartida. El ejemplo completo contiene temas posteriores; concéntrate ahora en la frontera de esta unidad. Las clases tienen imports y pertenencia a paquetes; los fragmentos de teoría son ilustraciones, no archivos Java sueltos para compilar.

### Archivos que debes localizar

- [SecurityConfig.java](../ejemplos/api-productos/src/main/java/com/lelyliliana/productos/SecurityConfig.java)
- [CsrfController.java](../ejemplos/api-productos/src/main/java/com/lelyliliana/productos/CsrfController.java)

Anota qué método o propiedad realiza la decisión descrita y qué parte depende del framework. Sigue su recorrido desde entrada hasta resultado; identifica un rechazo antes de ejecutar.

## Paso 3: ejecutar la prueba

```text
mvn -f ejemplos/api-productos/pom.xml "-Dtest=ProductoControllerTest" test
```

En Windows usa PowerShell; en Ubuntu/macOS usa terminal. Mantén comillas en -Dtest si incluye # para que se envíe como un solo argumento. Maven debe terminar con BUILD SUCCESS y cero fallos. La prueba comprueba la decisión descrita, no todas las capacidades de la aplicación. Lee qué dobles, base o contexto usa antes de atribuirle otra garantía.

## Paso 4: contrastar

Compara con la explicación del caso. Cuando hay HTTP, usa también [CONTRATO_API.md](../docs/CONTRATO_API.md) y el cliente de práctica; cuando hay persistencia, identifica la migración y el SQL. Los errores de permisos o entorno no se corrigen eliminando validación o desactivando controles.

## Paso 5: aplicar

Construye la matriz de cuatro solicitudes y explica qué control las rechaza.

Entrega predicción, comando o cambio, resultado y explicación. Resuelve [PRACTICA.md](PRACTICA.md) antes de abrir [SOLUCIONES.md](SOLUCIONES.md).

[Unidad](README.md) · [Comprobación completa](../docs/VERIFICACION.md)
