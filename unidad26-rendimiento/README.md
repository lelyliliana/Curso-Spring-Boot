# Unidad 26: Rendimiento y pruebas de carga

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

## Qué aprenderás
Medir antes de optimizar, diseñar carga reproducible y correlacionar latencia/throughput/errores con recursos y SQL.

# 1. Rendimiento es un requisito

Pregunta:
- ¿cuántas solicitudes?
- ¿qué latencia?
- ¿qué percentil?
- ¿qué tasa de error?
- ¿qué entorno?

“Que sea rápido” no es medible.

# 2. Métricas

**Latencia:** tiempo de respuesta.  
**Throughput:** trabajo por unidad de tiempo.  
**Errores:** respuestas/fallos.  
**Saturación:** CPU, memoria, pools, conexiones, etc.

Aumentar throughput puede empeorar latencia.

# 3. Carga vs estrés

Carga: comportamiento bajo demanda esperada.

Estrés: empujar más allá para observar límites/degradación.

Spike/soak son otros escenarios.

Define qué estás probando.

# 4. k6 u otra herramienta

Un escenario debe documentar:
- versión de app;
- datos;
- usuarios/tasa;
- duración;
- ramp-up;
- umbrales;
- máquina/entorno.

# 5. Warm-up

JVM/JIT, caches y pools pueden cambiar comportamiento inicial.

No descartes warm-up automáticamente; decide si quieres medir arranque frío o estado estable.

# 6. Cuello de botella

Puede estar en:
- SQL/N+1;
- pool de conexiones;
- API externa;
- CPU;
- GC/memoria;
- locks;
- logging;
- red.

No optimices Java si la consulta tarda 2 segundos.

# 7. Correlación

Durante carga mira:
```text
latencia ↔ CPU
latencia ↔ conexiones DB
errores ↔ timeout externo
throughput ↔ saturación
```

# 8. Optimización

Ciclo:
```text
medir → hipótesis → cambio → repetir misma prueba → comparar
```

Cambia una cosa importante por vez.

# 9. No extrapoles

Una prueba local con 10 usuarios no demuestra capacidad productiva con 10 000.

Sirve para aprender, comparar y encontrar algunos cuellos de botella bajo ese entorno.

# 10. Práctica guiada

Endpoint paginado:
1. baseline;
2. carga gradual;
3. observa métricas/SQL;
4. identifica cuello;
5. cambio;
6. repite.

# 11. Errores frecuentes
- optimizar sin baseline;
- solo promedio;
- cambiar varias cosas;
- ignorar errores;
- test sin datos representativos;
- extrapolar local.

# 12. Reto
Informe antes/después con escenario reproducible y conclusión limitada a evidencia.

# 13. Autoevaluación
1. ¿Latencia/throughput?
2. ¿Qué es saturación?
3. ¿Carga/estrés?
4. ¿Por qué warm-up?
5. ¿Qué es baseline?
6. ¿Prueba local demuestra producción?

# 14. Checklist
- [ ] Defino escenario.
- [ ] Mido varias métricas.
- [ ] Correlaciono.
- [ ] Optimizo con evidencia.

Continúa con producción.


---

## Caso desarrollado: Medir antes de modificar consultas o índices

La consulta paginada conserva orden y usa EntityGraph para categoría. No se concluye rendimiento por tener cuatro filas. scripts/carga.py mide GET público sobre un servidor local elegido, reporta latencia y errores y limita cantidad/concurrencia. No es una prueba distribuida ni demuestra capacidad en producción. Incluye calentamiento y registra versión, datos y hardware al comparar.

### Ejecutar y comprender

1. Prepara el [entorno de tu sistema](../docs/ENTORNO.md).
2. Sigue el [laboratorio completo](LABORATORIO.md), que identifica código, prueba y resultado.
3. Ejecuta desde la raíz:

```text
mvn -f ejemplos/api-productos/pom.xml "-Dtest=ProductoRepositoryTest#filtroYPaginaEstable" test
```

4. Resuelve la [práctica](PRACTICA.md).
5. Compara después con las [soluciones razonadas](SOLUCIONES.md).

### Reto explicado

Propón experimento N+1 y una mejora sin cambiar cinco cosas a la vez.

El objetivo es justificar una decisión con evidencia. No necesitas memorizar todas las anotaciones del proyecto avanzado para estudiar esta unidad.

## Continuar el curso

- **Unidad anterior:** [Unidad 25: Logging y trazabilidad](../unidad25-logging/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 27: Empaquetado y configuración de producción](../unidad27-produccion/README.md)
