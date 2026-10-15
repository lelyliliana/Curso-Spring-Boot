# Soluciones razonadas 02: Sobrescribir una propiedad sin recompilar

**Proyecto de esta unidad:** [laboratorios/fundamentos](../laboratorios/fundamentos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

SaludoProperties es un record @ConfigurationProperties validado; @EnableConfigurationProperties lo registra. application.properties fija Hola y máximo 40; la prueba sobrescribe el prefijo por Bienvenida. Una propiedad tipada no se registra automáticamente por ser record. En la API las contraseñas esenciales deben existir y los timeouts se validan al arrancar. Un perfil activa configuración, no almacena secretos de forma segura.

El código completo y sus imports están en [laboratorios/fundamentos](../laboratorios/fundamentos/README.md). La prueba `ContenedorTest#configuracionExterna` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Arranca el saludo con --saludo.prefijo=Buen día y prueba un máximo de nombre inválido.

**Solución y justificación:** El argumento completo es "--saludo.prefijo=Buen día". El saludo para Leli debe comenzar Buen día, Leli. El máximo 0 viola @Min y hace fallar el arranque. El recorrido fundamentos demuestra el prefijo externo con un JAR real; Configuración y ContenedorTest permiten contrastar enlace y ensamblaje con pruebas específicas.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Comando y contraejemplo

```text
mvn -f laboratorios/fundamentos/pom.xml package
java -jar laboratorios/fundamentos/target/fundamentos.jar "--saludo.prefijo=Buen día" --saludo.longitud-maxima=0
```
SaludoProperties declara longitudMaxima (en configuración, saludo.longitud-maxima). El valor 0 viola @Min(1) y debe rechazar la configuración al arrancar, no devolver 400 después de iniciar. Para el caso válido elimina la opción del máximo y consulta /api/saludos?nombre=Leli: espera Buen día, Leli. Configuración inválida y entrada HTTP inválida son fronteras diferentes.

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa fundamentos`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
