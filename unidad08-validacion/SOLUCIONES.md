# Soluciones razonadas 08: Distinguir forma válida de regla de negocio

**Proyecto de esta unidad:** [laboratorios/rest-memoria](../laboratorios/rest-memoria/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

En esta etapa no existen categoriaId, version ni credenciales. Si el enunciado menciona esos campos, resuelve primero el caso REST y completa esa comparación al llegar a JPA/integración.

## Decisión de referencia

@Valid activa las reglas del DTO: SKU con formato, nombre no vacío y precio positivo con hasta dos decimales. @Digits rechaza 1.001 antes del servicio. La verificación de categoría comienza en JPA, porque la etapa REST no la modela.

El código completo y sus imports están en [REST en memoria](../laboratorios/rest-memoria/README.md). La prueba `ProductoRestTest#validaDatos` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Prueba precio 1.001 y un SKU ya existente en REST; explica 400 y 409. Al llegar a JPA, añade categoría 99999 inexistente y explica 404.

**Solución y justificación:** El precio 1.001 falla antes del servicio con 400 y code VALIDATION_ERROR. El SKU existente produce 409 por estado. En JPA una categoriaId positiva inexistente llega al caso de uso y produce 404. No combines todos los errores en un mismo payload si quieres demostrar qué condición actúa. El recorrido REST comprueba también que los rechazos no crean registros.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Tres errores distintos

| Entrada en JPA/integración | Frontera | Resultado |
|---|---|---|
| precio 1.001 | @Digits en DTO | 400 VALIDATION_ERROR |
| categoriaId 99999 inexistente | Servicio | 404 NOT_FOUND |
| sku LIB-01 de datos originales | Regla/UNIQUE | 409 CONFLICT |

En REST solo se reproducen precio y SKU porque aún no hay categoría. Modifica una copia del JSON en cada petición, dejando válidos los demás campos. En la API integrada usa editor con CSRF; un 403 no demuestra que @Digits haya sido ejecutado. Después de cada rechazo consulta el catálogo para comprobar que no apareció un producto inválido.

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa rest`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
