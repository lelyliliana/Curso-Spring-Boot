# Soluciones razonadas 17: Ejecutar una tarea periódica sin prometer exclusión distribuida

**Proyecto de esta unidad:** [ejemplos/api-productos](../ejemplos/api-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

ResumenJob se activa solo con app.tareas-habilitadas=true. fixedDelay espera desde el final de una ejecución antes de programar la siguiente según scheduler; fixedRate refiere una frecuencia de programación distinta. Cada instancia de la aplicación puede ejecutar su propia tarea. @Scheduled no garantiza una única ejecución en un clúster ni persistencia de trabajo ante una caída.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `ConfiguracionTest#jobPuedeProbarseSinEsperarReloj` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Diseña qué cambiar si dos réplicas deben ejecutar una sola tarea de facturación.

**Solución y justificación:** Para una sola ejecución lógica entre réplicas necesitas coordinación compartida o un scheduler externo, identidad persistida del trabajo y una política de recuperación/repetición. @Scheduled actual solo programa el resumen local. La solución de esta unidad es un diseño justificado; no se anuncia un sistema distribuido implementado por activar la propiedad.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Dos réplicas y una sola ejecución

@Scheduled ejecuta en cada proceso habilitado. Un booleano local no coordina réplicas. Para facturación diseña una asignación persistente del trabajo con clave única (por ejemplo, periodo + cliente), reclamación transaccional y registro de resultado. Si el proceso cae, necesitas política de recuperación y operación idempotente.

Prueba dos trabajadores intentando reclamar la misma clave: solo uno debe adquirirla. Prueba también caída después del efecto externo y antes del registro. El curso incluye ResumenJob de lectura y prueba directa ejecutar; no presenta ese resumen como un coordinador distribuido de facturación.

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa integrado`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
