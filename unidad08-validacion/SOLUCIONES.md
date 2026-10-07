# Soluciones razonadas 08: Distinguir forma válida de regla de negocio

## Decisión de referencia

@Valid activa restricciones del DTO: SKU en mayúsculas, nombre no vacío, precio mínimo 0.01 con hasta dos decimales y categoría positiva. Esas restricciones no verifican existencia de categoría ni unicidad bajo concurrencia. El dominio vuelve a validar invariantes para llamadas fuera de MVC. La base añade constraints. Ninguna capa conoce reglas que no se escribieron.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `ProductoControllerTest#postInvalido` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Prueba precio 1.001, categoría 99999 y SKU ya existente; explica los tres errores.

**Solución y justificación:** 1.001 falla validación con 400; categoría positiva pero inexistente falla con 404; SKU repetido produce 409. Es forma, referencia y conflicto, respectivamente. No conviertas todas las excepciones en 400.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)
