# Soluciones razonadas 05: Enlazar rutas y parámetros con casos de uso

**Proyecto de esta unidad:** [laboratorios/rest-memoria](../laboratorios/rest-memoria/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

En esta etapa no existen categoriaId, version ni credenciales. Si el enunciado menciona esos campos, resuelve primero el caso REST y completa esa comparación al llegar a JPA/integración.

## Decisión de referencia

ProductoController recibe nombre, pagina y tamano. El servicio filtra sin distinguir mayúsculas, ordena por id y aplica límite. El catálogo inicia vacío: crea primero Bases de datos y Algoritmos con SKU distintos para poder predecir el resultado.

El código completo y sus imports están en [REST en memoria](../laboratorios/rest-memoria/README.md). La prueba `ProductoRestTest#paginacionYFiltro` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Consulta nombre=base y pagina=0,tamano=2; identifica cuántos productos entran.

**Solución y justificación:** La carga dev tiene Bases de datos como coincidencia. total=1, paginas=1 y contenido con LIB-02. Orden fijo por id. La prueba de slice sustituye el servicio y no demuestra consulta JPA; el test de repository cubre esa frontera.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Predicción y resultado

La etapa REST comienza vacía. Crea dos productos con SKU distintos: nombre "Bases de datos" y nombre "Algoritmos". Consulta:
```text
python scripts/peticion.py GET "http://localhost:8080/api/productos?nombre=base&pagina=0&tamano=2"
```
Espera contenido con solo Bases de datos, total 1 y paginas 1. En JPA con los datos originales el resultado también tiene un producto. tamano=2 es el máximo por página, no una obligación de devolver dos. Cambia pagina=1: el contenido queda vacío y total sigue siendo 1.
