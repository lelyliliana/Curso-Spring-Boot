# Soluciones razonadas 11: Observar SQL, identidad y unidad transaccional

## Decisión de referencia

@Entity mapea Producto; @Id y @GeneratedValue usan identidad; repository genera consultas; @Transactional rodea caso de uso. save no significa que cada cambio se haya confirmado: flush envía SQL y commit confirma la unidad. readOnly es una indicación de optimización, no un sistema de permisos que impide toda escritura. @Version detecta actualizaciones concurrentes cuando la base aplica su comparación de versión.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `ProductoRepositoryTest#relacionPersistida` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Describe dónde se confirma la creación y por qué se usa saveAndFlush.

**Solución y justificación:** El proxy transaccional inicia antes del servicio y confirma después de que retorna sin error. saveAndFlush fuerza constraints antes de construir respuesta, pero no confirma la transacción. Un fallo de commit aún impide enviar éxito del controller. Autoinvocación de un método @Transactional no pasa por ese proxy.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)
