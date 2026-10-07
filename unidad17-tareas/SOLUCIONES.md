# Soluciones razonadas 17: Ejecutar una tarea periódica sin prometer exclusión distribuida

## Decisión de referencia

ResumenJob se activa solo con app.tareas-habilitadas=true. fixedDelay espera desde el final de una ejecución antes de programar la siguiente según scheduler; fixedRate refiere una frecuencia de programación distinta. Cada instancia de la aplicación puede ejecutar su propia tarea. @Scheduled no garantiza una única ejecución en un clúster ni persistencia de trabajo ante una caída.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `ConfiguracionTest#jobPuedeProbarseSinEsperarReloj` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Diseña qué cambiar si dos réplicas deben ejecutar una sola tarea de facturación.

**Solución y justificación:** Necesitas coordinación (job central, cola, lock distribuido o scheduler apropiado) y operación idempotente/persistente. El resumen aquí solo lee count y no necesita liderar un clúster. Prueba ejecutar() directamente en lugar de dormir un minuto.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)
