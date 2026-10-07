# Soluciones razonadas 27: Promover un artefacto con configuración distinta

## Decisión de referencia

mvn verify ejecuta pruebas y produce target/api-productos.jar. El perfil prod usa PostgreSQL, valida mapeo y exige configuración externa. El mismo JAR se comprueba con H2 y PostgreSQL, sin recompilar por credenciales. server.shutdown=graceful y tiempo de espera controlan cierre, pero el balanceador y plataforma deben coordinar señales/tráfico.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `FlujoIntegrationTest` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Documenta el arranque prod y distingue valor secreto de una opción no secreta.

**Solución y justificación:** DB_PASSWORD y claves de identidades son secretos; puerto, URL y perfil son configuración (pueden ser sensibles según entorno). Usa variables o gestor de secretos fuera de Git. El verificador crea credenciales ficticias efímeras, no las propone para un sistema público.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)
