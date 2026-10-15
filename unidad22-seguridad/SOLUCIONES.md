# Soluciones razonadas 22: Probar identidad, autoridad y CSRF por separado

**Proyecto de esta unidad:** [ejemplos/api-productos](../ejemplos/api-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

GET del catálogo es público. Escritura exige PRODUCT_WRITE; métricas OPS_READ. Basic es educativo y necesita TLS fuera del entorno local. CSRF permanece activo porque los navegadores pueden reenviar credenciales Basic automáticamente. /api/csrf genera token ligado a sesión; el cliente conserva cookie y envía el header indicado. Un anónimo con token válido recibe 401; una petición sin CSRF puede recibir 403 antes de autenticación.

El código completo y sus imports están en [ejemplos/api-productos](../ejemplos/api-productos/README.md). La prueba `ProductoControllerTest` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Construye la matriz de cuatro solicitudes y explica qué control las rechaza.

**Solución y justificación:** La matriz es anónimo+CSRF válido →401; lector+CSRF válido →403 por permiso; editor sin CSRF →403; editor+CSRF válido →201 para una creación aceptada. El helper conserva cookies y obtiene el token sin imprimir claves. GET del catálogo es público; GET de pedidos no lo es. No generalices el permiso de una ruta a todas las demás.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

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

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa integrado`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
