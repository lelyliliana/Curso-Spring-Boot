# Soluciones razonadas 22: Probar identidad, autoridad y CSRF por separado

**Proyecto de esta unidad:** [ejemplos/api-productos](../ejemplos/api-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

GET del catálogo es público. Escritura exige PRODUCT_WRITE; métricas OPS_READ. Basic es educativo y necesita TLS fuera del entorno local. CSRF permanece activo porque los navegadores pueden reenviar credenciales Basic automáticamente. /api/csrf genera token ligado a sesión; el cliente conserva cookie y envía el header indicado. Un anónimo con token válido recibe 401; una petición sin CSRF puede recibir 403 antes de autenticación.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `ProductoControllerTest` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Construye la matriz de cuatro solicitudes y explica qué control las rechaza.

**Solución y justificación:** Anónimo+token: 401; lector+token: 403 por authority; editor sin token: 403 por CSRF; editor+token: llega al caso de uso. CORS no autentica clientes y stateless por sí solo no demuestra ausencia de riesgo CSRF. No guardes contraseñas en Git.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Matriz comprobable

| Solicitud de escritura | Resultado | Responsable |
|---|---:|---|
| Anónimo + CSRF válido | 401 | Autenticación |
| Lector + CSRF válido | 403 | Permiso PRODUCT_WRITE |
| Editor sin CSRF | 403 | Protección CSRF |
| Editor + CSRF válido + entrada válida | 201 | Controller/servicio |

ProductoControllerTest incluye los tres rechazos y creacionContrato. El cliente peticion.py obtiene el token y conserva cookie en cada escritura. --sin-csrf se usa para las etapas educativas que no tienen Security o para probar un rechazo explícito; no es una corrección de permisos en la API integrada.
