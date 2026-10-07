# Soluciones razonadas 00: Arrancar una aplicación y reconocer el puerto

## Decisión de referencia

El proyecto fundamentos contiene solo MVC, validación y pruebas. Inicia en 127.0.0.1:8080; GET /api/saludos?nombre=Leli devuelve mensaje Hola, Leli y la fecha UTC actual. Un 404 indica que respondió un servidor, no que Java esté caído. mvn package ejecuta las fases anteriores del ciclo, incluidas pruebas; no necesitas repetir mvn test antes de cada package.

El código completo y sus imports están en [laboratorios/fundamentos](../laboratorios/fundamentos/README.md). La prueba `ContenedorTest` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Ejecuta el mismo JAR en 8081 y escribe el comando que prueba que cambió el puerto.

**Solución y justificación:** java -jar target/fundamentos.jar --server.port=8081; consulta http://localhost:8081/api/saludos. Cambió configuración externa, no el código ni el artefacto.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)
