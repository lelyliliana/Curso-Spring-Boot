# Soluciones razonadas 28: Construir imagen y comprender red y persistencia

**Proyecto de esta unidad:** [ejemplos/api-productos](../ejemplos/api-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

Dockerfile copia el JAR y ejecuta UID 10001. La imagen base tiene una etiqueta de Java 21 y se debe actualizar deliberadamente o fijar digest en un release; etiqueta no garantiza los mismos bytes para siempre. localhost dentro del contenedor es ese contenedor. En Docker Desktop los contenedores Linux usan un kernel Linux de su entorno virtual, no el kernel Windows/macOS directamente.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `FlujoIntegrationTest` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Conecta PostgreSQL como servicio db y evita exponer la DB a Internet.

**Solución y justificación:** Compose usa jdbc:postgresql://db:5432/productos y volumen para PostgreSQL. Publica solo la API en loopback del equipo y recibe claves del entorno. La CI Linux construye/ejecuta la imagen; Windows/macOS verifican el JAR sin asumir Docker instalado.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Comprobar la conexión entre servicios

En compose.yml, DB_URL usa jdbc:postgresql://db:5432/productos. Dentro del contenedor api, localhost sería ese mismo contenedor, no PostgreSQL. El servicio db no tiene ports; solo api publica 127.0.0.1:8080:8080.

```text
docker compose -f ejemplos/api-productos/compose.yml up --build
docker compose -f ejemplos/api-productos/compose.yml ps
```
Configura las claves antes según OPERACION. Consulta health y crea categoría/producto. Detén/recrea conservando datos_pg y comprueba que el producto sigue presente. No uses down -v para demostrar persistencia, porque elimina el volumen.
