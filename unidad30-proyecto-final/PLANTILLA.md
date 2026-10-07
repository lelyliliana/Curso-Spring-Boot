# Memoria del proyecto final

## 1. Problema y alcance

Describe usuario, necesidad y flujo principal con un ejemplo ficticio. Define tres recursos, al menos una relación y una regla que requiera transacción. Explica qué queda fuera del alcance y por qué.

## 2. Contrato

Tabla de método, ruta, entrada, salida, permisos y códigos. Incluye creación con Location, paginación limitada, actualización concurrente y eliminación. Proporciona solicitudes/respuestas ficticias para éxito y rechazo. Distingue validación sintáctica, recurso ausente y conflicto.

## 3. Modelo y decisiones

Diagrama de entidades con cardinalidades, restricciones y versiones. Explica capas, mapeo DTO, límites transaccionales, carga de relaciones, claves únicas y migraciones. Justifica decisiones con una alternativa y su coste.

## 4. Implementación reproducible

Java/Boot/Maven, estructura de archivos, configuración externa y perfiles. Incluye comandos de pruebas, empaquetado y ejecución para Bash y PowerShell cuando cambia la sintaxis. Prueba JAR con H2 y PostgreSQL; registra versiones utilizadas. Nunca incluyas credenciales reales.

## 5. Seguridad y errores

Tabla de permisos, autenticación educativa, CSRF, exposición de Actuator y respuesta genérica ante errores internos. Explica requisitos pendientes para una plataforma pública. Comprueba que un usuario sin autoridad no modifica datos.

## 6. Matriz de pruebas

Para cada regla: caso normal, límite, rechazo y capa de prueba. Adjunta resultados Maven y HTTP del JAR. Incluye una carrera de escritura, comprobación del motor real y limpieza del entorno temporal. Un porcentaje de cobertura no sustituye estas pruebas.

## 7. Operación

Salud, readiness, trazabilidad, timeout, política de reintentos, tareas e impacto de varias instancias. Registra una medición local de carga con contexto. Explica contenedor, red, persistencia, actualización y respaldo/restauración.

## 8. Demostración

Secuencia breve: iniciar, crear, consultar, actualizar, rechazar versión antigua y comprobar que el estado es correcto. Mostrar permiso denegado, health y log correlacionado. Concluir con limitaciones concretas y siguiente mejora sustentada por evidencia.

[Checklist](CHECKLIST.md) · [Rúbrica](RUBRICA.md) · [Unidad](README.md)
