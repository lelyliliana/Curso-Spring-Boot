# Soluciones razonadas 27: Promover un artefacto con configuración distinta

**Proyecto de esta unidad:** [ejemplos/api-productos](../ejemplos/api-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

mvn verify ejecuta pruebas y produce target/api-productos.jar. El perfil prod usa PostgreSQL, valida mapeo y exige configuración externa. El mismo JAR se comprueba con H2 y PostgreSQL, sin recompilar por credenciales. server.shutdown=graceful y tiempo de espera controlan cierre, pero el balanceador y plataforma deben coordinar señales/tráfico.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `FlujoIntegrationTest` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Documenta el arranque prod y distingue valor secreto de una opción no secreta.

**Solución y justificación:** Usa SPRING_PROFILES_ACTIVE=prod y proporciona DB_URL,DB_USER,DB_PASSWORD y las claves de identidades externamente. El artefacto sigue siendo target/api-productos.jar. No promociones la base H2 dev como producción ni incluyas contraseñas en el README. El verificador compara el JAR en bases aisladas; una construcción correcta no demuestra por sí sola un despliegue operativo.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Separar opciones de secretos

| Variable | Ejemplo/uso | ¿Secreto? |
|---|---|---|
| SPRING_PROFILES_ACTIVE | prod | No |
| DB_URL | JDBC de tu base local | No en este ejemplo; evita URL con clave |
| DB_USER | Rol de práctica | No es una contraseña |
| DB_PASSWORD | Clave externa | Sí |
| APP_EDITOR_PASSWORD/APP_LECTOR_PASSWORD | Claves externas | Sí |

Construye con mvn -f ejemplos/api-productos/pom.xml verify y ejecuta java -jar ejemplos/api-productos/target/api-productos.jar en la terminal con variables configuradas. Verifica que prod no carga V3 ficticia. Los comandos exactos por sistema están en ENTORNO; la preparación de PostgreSQL está en OPERACION.

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa integrado`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
