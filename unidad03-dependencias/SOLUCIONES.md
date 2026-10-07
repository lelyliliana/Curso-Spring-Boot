# Soluciones razonadas 03: Construir y probar dependencias explícitas

## Decisión de referencia

SaludoServiceTest crea el servicio con new, un Clock fijo y configuración. La respuesta de enero de 2026 es determinista sin contexto Spring. Spring puede ensamblar los mismos objetos en producción; la lógica no necesita buscar beans. Un único constructor no requiere @Autowired. Si aparecen dos Clock compatibles, declara una selección consciente con @Qualifier, @Primary o configuración explícita.

El código completo y sus imports están en [laboratorios/fundamentos](../laboratorios/fundamentos/README.md). La prueba `SaludoServiceTest#constructorSinSpring` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Comprueba nombres vacíos y el límite de 40 caracteres sin arrancar el servidor.

**Solución y justificación:** nombreObligatorio y limite prueban esos casos. Una frontera 40 es válida y 41 falla. El reloj se fija en UTC; cambiar fecha real del equipo no altera la prueba.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)
