# Soluciones razonadas 00: Arrancar una aplicación y reconocer el puerto

**Proyecto de esta unidad:** [laboratorios/fundamentos](../laboratorios/fundamentos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

El proyecto fundamentos contiene solo MVC, validación y pruebas. Inicia en 127.0.0.1:8080; GET /api/saludos?nombre=Leli devuelve mensaje Hola, Leli y la fecha UTC actual. Un 404 indica que respondió un servidor, no que Java esté caído. mvn package ejecuta las fases anteriores del ciclo, incluidas pruebas; no necesitas repetir mvn test antes de cada package.

El código completo y sus imports están en [laboratorios/fundamentos](../laboratorios/fundamentos/README.md). La prueba `ContenedorTest` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Ejecuta el mismo JAR en 8081 y escribe el comando que prueba que cambió el puerto.

**Solución y justificación:** Desde la raíz ejecuta java -jar laboratorios/fundamentos/target/fundamentos.jar --server.port=8081. En otra terminal usa python scripts/peticion.py GET "http://127.0.0.1:8081/api/saludos?nombre=Leli". Debe responder 200 con mensaje y fecha. La consulta a 8080 no comprueba el cambio y podría incluso corresponder a otro proceso. El artefacto es el mismo.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Reproducir la solución

1. Construye el JAR desde la raíz:
```text
mvn -f laboratorios/fundamentos/pom.xml package
```
2. Inícialo en 8081:
```text
java -jar laboratorios/fundamentos/target/fundamentos.jar --server.port=8081
```
3. En otra terminal desde la raíz:
```text
python scripts/peticion.py GET http://localhost:8081/api/saludos?nombre=Leli
```
Espera 200 y mensaje Hola, Leli. Un error de conexión a 8080 solo prueba que allí no hay un servidor accesible; la petición a 8081 prueba el puerto utilizado.

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa fundamentos`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
