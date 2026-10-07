# Soluciones razonadas 18: Probar reglas sin cargar el contexto

## Decisión de referencia

ProductoServiceTest construye servicio con repositorios Mockito. Usa objetos reales de dominio y BigDecimal; no mockea valores simples. Comprueba que SKU duplicado y categoría ausente no persisten. Un test unitario no demuestra UNIQUE ni transacción real: esos riesgos van a repository/integración. No se afirma 404 en el servicio; se afirma una excepción de dominio.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `ProductoServiceTest` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Añade caso válido en el dominio y un precio inválido, y justifica qué no se prueba.

**Solución y justificación:** entidadValidaFueraDeSpring normaliza espacios y conserva decimal. precioInvalido verifica 0, negativo, fracciones excesivas y desbordamiento del contrato. No demuestra serialización ni SQL, que se prueban en otras fronteras.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)
