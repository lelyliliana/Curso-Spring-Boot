# Soluciones razonadas 28: Construir imagen y comprender red y persistencia

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
