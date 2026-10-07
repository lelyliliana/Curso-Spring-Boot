# Unidad 16: Timeouts, retries y resiliencia básica

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

## Qué aprenderás
Diseñar comportamiento ante dependencias lentas o fallidas sin empeorar el incidente.

# 1. La red falla

Una dependencia puede no conectar, tardar, cortar conexión, devolver 429/500 o responder datos inválidos.

Diseñar solo el camino feliz no es suficiente.

# 2. Timeout

Sin límites, recursos pueden quedar esperando demasiado.

Distingue según cliente:
- conexión;
- lectura/respuesta;
- límite total de operación.

Configura según contexto, no copiando un número universal.

# 3. Retry

Reintentar puede ayudar ante fallos transitorios.

Pero una operación de cobro repetida podría duplicar efectos si no existe idempotencia.

Pregunta:
- ¿operación idempotente?
- ¿fallo transitorio?
- ¿cuántos intentos?
- ¿qué espera entre intentos?

# 4. Backoff y jitter

Reintentar inmediatamente desde muchos clientes puede empeorar una caída.

Backoff aumenta espera; jitter distribuye reintentos.

# 5. 429

Too Many Requests puede incluir `Retry-After`.

Respeta el contrato del proveedor cuando aplique.

# 6. Circuit breaker

Estados conceptuales:

```text
CLOSED → llamadas normales
OPEN → falla rápido
HALF_OPEN → prueba recuperación
```

Evita insistir sobre una dependencia degradada, pero añade estado/configuración.

# 7. Fallback

Válido:
> usar último dato, claramente marcado como desactualizado.

Peligroso:
> inventar precio 0 como si fuera real.

Debe preservar semántica.

# 8. Bulkhead

Separar recursos puede impedir que una dependencia lenta consuma toda la capacidad.

# 9. Librerías

Herramientas como Resilience4j implementan patrones.

Primero diseña política; después configura librería.

# 10. Práctica guiada

Para timeout, 404, 429 y 500 define:
- si reintentas;
- qué devuelve tu aplicación;
- qué registras/mides.

# 11. Errores frecuentes
- retry para todo;
- sin timeout;
- fallback que miente;
- circuit breaker sin necesidad;
- reintentos amplificando carga.

# 12. Reto
Política completa para una dependencia con pruebas simulando fallos.

# 13. Autoevaluación
1. ¿Por qué timeout?
2. ¿Cuándo retry es peligroso?
3. ¿Qué es backoff?
4. ¿Qué significa 429?
5. ¿Qué hace circuit breaker?
6. ¿Qué exige un fallback?

# 14. Checklist
- [ ] Limito espera.
- [ ] Reintento con criterio.
- [ ] No invento datos.
- [ ] Diseño degradación.

Continúa con tareas.


---

## Caso desarrollado: Acotar fallos y reintentos

Por defecto intentos=1. Para el experimento se admite 2 y se repiten solo respuestas 502/503/504 de este GET. 404, 429, timeout y JSON inválido no se reintentan automáticamente. El ejemplo no implementa circuit breaker ni un presupuesto de tiempo global; dos intentos pueden aumentar la latencia. En un sistema real define backoff/jitter, presupuesto total y política de Retry-After si aplica.

### Ejecutar y comprender

1. Prepara el [entorno de tu sistema](../docs/ENTORNO.md).
2. Sigue el [laboratorio completo](LABORATORIO.md), que identifica código, prueba y resultado.
3. Ejecuta desde la raíz:

```text
mvn -f ejemplos/api-productos/pom.xml "-Dtest=ProveedorClientTest" test
```

4. Resuelve la [práctica](PRACTICA.md).
5. Compara después con las [soluciones razonadas](SOLUCIONES.md).

### Reto explicado

Justifica por qué no copiar este retry a un POST de pago.

El objetivo es justificar una decisión con evidencia. No necesitas memorizar todas las anotaciones del proyecto avanzado para estudiar esta unidad.

## Continuar el curso

- **Unidad anterior:** [Unidad 15: Consumo de APIs externas](../unidad15-apis-externas/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 17: Tareas programadas y procesamiento periódico](../unidad17-tareas/README.md)
