# Soluciones razonadas 01: Ver qué objetos administra el contenedor

## Decisión de referencia

FundamentosApplication declara Clock mediante @Bean. SaludoService se descubre por @Service y el constructor recibe Clock y SaludoProperties. Saludo es un record creado por petición, no un bean. El singleton del servicio es por contenedor/definición; debe evitar guardar estado mutable específico de un usuario. ContenedorTest comprueba identidad del bean y existencia de Clock.

El código completo y sus imports están en [laboratorios/fundamentos](../laboratorios/fundamentos/README.md). La prueba `ContenedorTest#beansSingleton` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Dibuja el grafo del saludo y explica por qué el DTO no lleva @Component.

**Solución y justificación:** Controller depende de Service; Service depende de Clock y configuración. DTO representa un resultado con datos diferentes por llamada. Anotar DTO como componente requeriría resolver sus valores como dependencias y compartiría un objeto sin corresponder al requisito.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)
