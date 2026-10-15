# Recorridos guiados de Spring Boot

[Índice](../README.md) · [Ruta progresiva](RUTA_PROGRESIVA.md) · [Verificación](VERIFICACION.md)

## Antes de ejecutar

Los cuatro proyectos conservan su código completo y su pom.xml. No hay pom en la raíz. Construye la etapa elegida con el comando de su LABORATORIO o de su README. El recorrido necesita su JAR ya construido y no ejecuta Maven por su cuenta.

Para comenzar con fundamentos, desde la raíz:

```text
python scripts/recorridos.py --etapa fundamentos
```

El programa inicia una aplicación propia, elige un puerto local temporal, usa datos ficticios y la detiene al terminar. El cliente muestra método, ruta, HTTP recibido y esperado; las comprobaciones también revisan cuerpo, headers y estado posterior. Las claves educativas de la integrada se generan durante el proceso y no se imprimen.

No dirige comandos a una base personal ni cambia una aplicación que ya tengas abierta. Descarta la configuración Spring/DB heredada de la terminal y las opciones Java externas; las etapas JPA/integrada usan el perfil dev empaquetado con H2 en memoria. PostgreSQL y Docker tienen verificaciones separadas y se describen como ejecutadas solo cuando se realizaron.

## Elegir una etapa

Ejecuta un comando a la vez después de construir el proyecto correspondiente:

| Opción | Proyecto/JAR requerido | Casos |
|---|---|---|
| `--etapa fundamentos` | laboratorios/fundamentos/target/fundamentos.jar | 1 y 2 |
| `--etapa rest` | laboratorios/rest-memoria/target/rest-memoria.jar | 3 a 5 |
| `--etapa jpa` | laboratorios/jpa-productos/target/jpa-productos.jar | 6 y 7 |
| `--etapa integrado` | ejemplos/api-productos/target/api-productos.jar | 8 a 12 |
| `--etapa todas` | Los cuatro anteriores | Los doce casos |

El código completo está en [recorridos.py](../scripts/recorridos.py), con el cliente [peticion.py](../scripts/peticion.py) y el contexto local de [verificar.py](../scripts/verificar.py). La verificación completa incluye estos doce casos después de sus contratos operativos. Una excepción detiene el recorrido y conserva el log de la aplicación en salidas para diagnóstico.

## Qué se observa

| Caso | Decisión | Evidencia |
|---|---|---|
| 1 | Configuración externa | Mismo JAR, prefijo Buen día y fecha ISO |
| 2 | Nombre inválido | 400 por blanco o longitud 41 |
| 3 | Creación REST | 201, Location, JSON y solo los campos de esta etapa |
| 4 | Paginación | Una coincidencia; página vacía conserva total |
| 5 | Causas de rechazo | 400 por forma, 409 por SKU y 404 por ausencia |
| 6 | Versión antigua | Actualización válida, conflicto y consulta conservada |
| 7 | Relación persistente | Categoría inexistente y página fuera del rango aceptado |
| 8 | Identidad/permiso/CSRF | 401,403,403,201 variando una condición |
| 9 | Correlación | Id válido conservado e inválido reemplazado |
| 10 | Operación | Salud pública acotada y métricas protegidas |
| 11 | Historial | Captura al confirmar, precio posterior independiente, cancelación y rechazo |
| 12 | Integración externa | 503 con contrato local sin traza interna |

Las peticiones automáticas de CSRF y de espera de arranque no forman parte del contador de peticiones mostrado por la clase Recorrido. La prueba concurrente de dos confirmaciones pertenece además al verificador operativo; el caso 11 guiado explica la secuencia del historial sin presentarla como una carrera concurrente.

## Desarrollos por unidad

| Unidad | Recorrido del código | Fuente de la práctica |
|---|---|---|
| 00 | [Seguir el arranque hasta una respuesta HTTP](../unidad00-entorno/DESARROLLO.md) | [Práctica](../unidad00-entorno/PRACTICA.md) · [Solución](../unidad00-entorno/SOLUCIONES.md) |
| 01 | [Distinguir objetos administrados y resultados por petición](../unidad01-spring-ioc/DESARROLLO.md) | [Práctica](../unidad01-spring-ioc/PRACTICA.md) · [Solución](../unidad01-spring-ioc/SOLUCIONES.md) |
| 02 | [Separar configuración válida, valor por defecto y error de arranque](../unidad02-configuracion/DESARROLLO.md) | [Práctica](../unidad02-configuracion/PRACTICA.md) · [Solución](../unidad02-configuracion/SOLUCIONES.md) |
| 03 | [Probar una dependencia sin abrir el servidor](../unidad03-dependencias/DESARROLLO.md) | [Práctica](../unidad03-dependencias/PRACTICA.md) · [Solución](../unidad03-dependencias/SOLUCIONES.md) |
| 04 | [Definir identidad y errores antes de añadir persistencia](../unidad04-http-rest/DESARROLLO.md) | [Práctica](../unidad04-http-rest/PRACTICA.md) · [Solución](../unidad04-http-rest/SOLUCIONES.md) |
| 05 | [Separar total de coincidencias y contenido de una página](../unidad05-controllers/DESARROLLO.md) | [Práctica](../unidad05-controllers/PRACTICA.md) · [Solución](../unidad05-controllers/SOLUCIONES.md) |
| 06 | [Comprobar una creación por sus tres partes](../unidad06-respuestas-http/DESARROLLO.md) | [Práctica](../unidad06-respuestas-http/PRACTICA.md) · [Solución](../unidad06-respuestas-http/SOLUCIONES.md) |
| 07 | [Leer el contrato del DTO de la etapa que estás ejecutando](../unidad07-dto/DESARROLLO.md) | [Práctica](../unidad07-dto/PRACTICA.md) · [Solución](../unidad07-dto/SOLUCIONES.md) |
| 08 | [Distinguir validación de entrada y reglas con estado](../unidad08-validacion/DESARROLLO.md) | [Práctica](../unidad08-validacion/PRACTICA.md) · [Solución](../unidad08-validacion/SOLUCIONES.md) |
| 09 | [Traducir errores sin convertir diagnóstico interno en contrato](../unidad09-errores/DESARROLLO.md) | [Práctica](../unidad09-errores/PRACTICA.md) · [Solución](../unidad09-errores/SOLUCIONES.md) |
| 10 | [Asignar una garantía a la capa que puede cumplirla](../unidad10-capas/DESARROLLO.md) | [Práctica](../unidad10-capas/PRACTICA.md) · [Solución](../unidad10-capas/SOLUCIONES.md) |
| 11 | [Separar persistir, enviar SQL y confirmar una transacción](../unidad11-jpa/DESARROLLO.md) | [Práctica](../unidad11-jpa/PRACTICA.md) · [Solución](../unidad11-jpa/SOLUCIONES.md) |
| 12 | [Relacionar propiedad del modelo y borrado de datos compartidos](../unidad12-relaciones/DESARROLLO.md) | [Práctica](../unidad12-relaciones/PRACTICA.md) · [Solución](../unidad12-relaciones/SOLUCIONES.md) |
| 13 | [Conservar un orden reproducible al paginar](../unidad13-consultas/DESARROLLO.md) | [Práctica](../unidad13-consultas/PRACTICA.md) · [Solución](../unidad13-consultas/SOLUCIONES.md) |
| 14 | [Promover esquema y configuración sin trasladar datos de demostración](../unidad14-base-datos/DESARROLLO.md) | [Práctica](../unidad14-base-datos/PRACTICA.md) · [Solución](../unidad14-base-datos/SOLUCIONES.md) |
| 15 | [Convertir una respuesta externa en un contrato propio](../unidad15-apis-externas/DESARROLLO.md) | [Práctica](../unidad15-apis-externas/PRACTICA.md) · [Solución](../unidad15-apis-externas/SOLUCIONES.md) |
| 16 | [Limitar intentos sin confundirlos con un plazo total](../unidad16-resiliencia/DESARROLLO.md) | [Práctica](../unidad16-resiliencia/PRACTICA.md) · [Solución](../unidad16-resiliencia/SOLUCIONES.md) |
| 17 | [Delimitar una tarea local y una coordinación entre réplicas](../unidad17-tareas/DESARROLLO.md) | [Práctica](../unidad17-tareas/PRACTICA.md) · [Solución](../unidad17-tareas/SOLUCIONES.md) |
| 18 | [Probar reglas sin atribuir al mock garantías de la base](../unidad18-pruebas-unitarias/DESARROLLO.md) | [Práctica](../unidad18-pruebas-unitarias/PRACTICA.md) · [Solución](../unidad18-pruebas-unitarias/SOLUCIONES.md) |
| 19 | [Superar filtros para demostrar validación del cuerpo](../unidad19-mockmvc/DESARROLLO.md) | [Práctica](../unidad19-mockmvc/PRACTICA.md) · [Solución](../unidad19-mockmvc/SOLUCIONES.md) |
| 20 | [Forzar una restricción y reconocer el motor que la ejecutó](../unidad20-pruebas-jpa/DESARROLLO.md) | [Práctica](../unidad20-pruebas-jpa/PRACTICA.md) · [Solución](../unidad20-pruebas-jpa/SOLUCIONES.md) |
| 21 | [Comprobar el dato posterior a una operación rechazada](../unidad21-integracion-tests/DESARROLLO.md) | [Práctica](../unidad21-integracion-tests/PRACTICA.md) · [Solución](../unidad21-integracion-tests/SOLUCIONES.md) |
| 22 | [Identificar el control que rechaza cada petición](../unidad22-seguridad/DESARROLLO.md) | [Práctica](../unidad22-seguridad/PRACTICA.md) · [Solución](../unidad22-seguridad/SOLUCIONES.md) |
| 23 | [Distinguir salud agregada, proceso vivo y disponibilidad para tráfico](../unidad23-actuator/DESARROLLO.md) | [Práctica](../unidad23-actuator/PRACTICA.md) · [Solución](../unidad23-actuator/SOLUCIONES.md) |
| 24 | [Diseñar métricas con un número acotado de combinaciones](../unidad24-metricas/DESARROLLO.md) | [Práctica](../unidad24-metricas/PRACTICA.md) · [Solución](../unidad24-metricas/SOLUCIONES.md) |
| 25 | [Conservar correlación sin confiar ciegamente en un header](../unidad25-logging/DESARROLLO.md) | [Práctica](../unidad25-logging/PRACTICA.md) · [Solución](../unidad25-logging/SOLUCIONES.md) |
| 26 | [Comparar una hipótesis de consultas con datos y resultados iguales](../unidad26-rendimiento/DESARROLLO.md) | [Práctica](../unidad26-rendimiento/PRACTICA.md) · [Solución](../unidad26-rendimiento/SOLUCIONES.md) |
| 27 | [Ejecutar el mismo artefacto con configuración de destino](../unidad27-produccion/DESARROLLO.md) | [Práctica](../unidad27-produccion/PRACTICA.md) · [Solución](../unidad27-produccion/SOLUCIONES.md) |
| 28 | [Entender qué significa localhost dentro de cada contenedor](../unidad28-docker/DESARROLLO.md) | [Práctica](../unidad28-docker/PRACTICA.md) · [Solución](../unidad28-docker/SOLUCIONES.md) |
| 29 | [Entregar un recorrido reconstruible de la API](../unidad29-taller/DESARROLLO.md) | [Práctica](../unidad29-taller/PRACTICA.md) · [Solución](../unidad29-taller/SOLUCIONES.md) |
| 30 | [Mantener historial y transiciones dentro de una transacción](../unidad30-proyecto-final/DESARROLLO.md) | [Práctica](../unidad30-proyecto-final/PRACTICA.md) · [Solución](../unidad30-proyecto-final/SOLUCIONES.md) |
