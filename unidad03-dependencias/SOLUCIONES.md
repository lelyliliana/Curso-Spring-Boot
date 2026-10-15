# Soluciones razonadas 03: Construir y probar dependencias explícitas

**Proyecto de esta unidad:** [laboratorios/fundamentos](../laboratorios/fundamentos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

SaludoServiceTest crea el servicio con new, un Clock fijo y configuración. La respuesta de enero de 2026 es determinista sin contexto Spring. Spring puede ensamblar los mismos objetos en producción; la lógica no necesita buscar beans. Un único constructor no requiere @Autowired. Si aparecen dos Clock compatibles, declara una selección consciente con @Qualifier, @Primary o configuración explícita.

El código completo y sus imports están en [laboratorios/fundamentos](../laboratorios/fundamentos/README.md). La prueba `SaludoServiceTest#constructorSinSpring` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Comprueba nombres vacíos y el límite de 40 caracteres sin arrancar el servidor.

**Solución y justificación:** Desde la raíz usa mvn -f laboratorios/fundamentos/pom.xml -Dtest=SaludoServiceTest test. La suite construye servicio, reloj y configuración sin contexto Spring. Vacío/blanco y 41 unidades se rechazan; 40 se acepta. La fecha depende del Clock fijo y no de modificar el reloj real del equipo. Una prueba del servicio espera una excepción, no un status HTTP.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Prueba sin servidor

Abre SaludoServiceTest. Se construye el servicio con Clock.fixed y propiedades, sin @SpringBootTest. Comprueba estas tres entradas:

| Entrada | Resultado |
|---|---|
| " " | IllegalArgumentException |
| "a" repetida 40 veces | Respuesta válida |
| "a" repetida 41 veces | IllegalArgumentException |

```text
mvn -f laboratorios/fundamentos/pom.xml "-Dtest=SaludoServiceTest" test
```
La fecha fija evita que el resultado dependa del día. Esta prueba ejercita la lógica Java; no demuestra que Spring resuelva el constructor ni que el controller responda 400.

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa fundamentos`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
