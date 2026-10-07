# Soluciones razonadas 02: Sobrescribir una propiedad sin recompilar

## Decisión de referencia

SaludoProperties es un record @ConfigurationProperties validado; @EnableConfigurationProperties lo registra. application.properties fija Hola y máximo 40; la prueba sobrescribe el prefijo por Bienvenida. Una propiedad tipada no se registra automáticamente por ser record. En la API las contraseñas esenciales deben existir y los timeouts se validan al arrancar. Un perfil activa configuración, no almacena secretos de forma segura.

El código completo y sus imports están en [laboratorios/fundamentos](../laboratorios/fundamentos/README.md). La prueba `ContenedorTest#configuracionExterna` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Arranca el saludo con --saludo.prefijo=Buen día y prueba un máximo de nombre inválido.

**Solución y justificación:** Usa comillas en el argumento completo si contiene espacios. --saludo.longitud-maxima=0 debe impedir iniciar por @Min; no se convierte silenciosamente en 40. Observa el origen de configuración efectiva.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)
