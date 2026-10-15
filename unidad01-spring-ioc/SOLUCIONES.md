# Soluciones razonadas 01: Ver qué objetos administra el contenedor

**Proyecto de esta unidad:** [laboratorios/fundamentos](../laboratorios/fundamentos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

FundamentosApplication declara Clock mediante @Bean. SaludoService se descubre por @Service y el constructor recibe Clock y SaludoProperties. Saludo es un record creado por petición, no un bean. El singleton del servicio es por contenedor/definición; debe evitar guardar estado mutable específico de un usuario. ContenedorTest comprueba identidad del bean y existencia de Clock.

El código completo y sus imports están en [laboratorios/fundamentos](../laboratorios/fundamentos/README.md). La prueba `ContenedorTest#beansSingleton` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Dibuja el grafo del saludo y explica por qué el DTO no lleva @Component.

**Solución y justificación:** El grafo es Controller → Service; Service → Clock y SaludoProperties. Saludo sale de responder como dato nuevo por llamada. ContenedorTest puede comprobar identidad del bean, pero no demuestra que cualquier campo mutable añadido sea seguro entre solicitudes. El DTO no lleva @Component porque su contenido no representa una dependencia de arranque.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Grafo de referencia

| Objeto | Lo crea | Depende de |
|---|---|---|
| SaludoController | Contenedor | SaludoService |
| SaludoService | Contenedor | Clock y SaludoProperties |
| Clock | Método @Bean | Reloj UTC |
| SaludoProperties | Binding de configuración | Propiedades externas |
| Saludo (DTO) | Método del servicio por respuesta | Mensaje y fecha |

Saludo cambia en cada respuesta; el servicio singleton no debe guardar el nombre del usuario en un campo. Lee la creación con new Saludo en SaludoService y compara con contexto.getBean(Clock.class) de ContenedorTest. Quitar @Service rompe la resolución del servicio; agregar @Component al record no resuelve su responsabilidad.

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa fundamentos`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
