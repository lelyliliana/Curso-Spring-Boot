# Unidad 17 — Tareas programadas y procesamiento periódico

## Qué aprenderás
Programar trabajos, comprender solapamiento y múltiples instancias, y diseñar idempotencia/trazabilidad.

# 1. Scheduled

```java
@Scheduled(fixedDelayString = "${app.sync.delay}")
public void sincronizar() {
    // trabajo
}
```

Externaliza intervalos que cambian por entorno.

# 2. fixedRate vs fixedDelay

Conceptualmente:
- fixedRate mantiene una frecuencia de programación;
- fixedDelay espera después de finalizar antes de programar la siguiente.

La ejecución real depende también del scheduler configurado.

# 3. Cron y zona

Cuando el requisito es hora local:

```java
@Scheduled(
    cron = "...",
    zone = "America/Bogota"
)
```

No dependas accidentalmente de la zona del servidor.

# 4. Solapamiento

Si la tarea tarda más que el intervalo, pregunta:
- ¿puede haber dos ejecuciones?
- ¿qué scheduler usamos?
- ¿qué recursos comparten?

# 5. Varias instancias

```text
app-1 → tarea
app-2 → tarea
app-3 → tarea
```

Scheduled por sí solo no coordina un clúster.

# 6. Idempotencia

Si el mismo elemento se procesa dos veces, el segundo intento no debería duplicar efectos indebidos.

Ayudan:
- claves;
- constraints;
- estados;
- operaciones idempotentes.

# 7. Coordinación distribuida

Locks distribuidos u otros mecanismos pueden garantizar exclusión global, pero añaden infraestructura.

Solo cuando el requisito lo necesita.

# 8. Trazabilidad

Registra:
- inicio;
- fin;
- procesados;
- éxitos;
- fallos;
- identificador de ejecución.

# 9. Fallo parcial

De 1000 registros falla el 700.

Decide:
- transacción total;
- por elemento/lote;
- checkpoint;
- reintento.

Una transacción gigantesca no es automáticamente correcta.

# 10. Práctica guiada

Diseña sincronización:
1. obtiene elementos;
2. procesa por id;
3. evita duplicados;
4. registra ejecución;
5. falla a mitad;
6. repite.

# 11. Errores frecuentes
- asumir una instancia;
- cron sin zona;
- tarea no idempotente;
- transacción enorme;
- no registrar resultado;
- capturar excepción y reportar éxito.

# 12. Reto
Sincronización periódica repetible con trazabilidad y política de fallo.

# 13. Autoevaluación
1. ¿fixedRate/delay?
2. ¿Por qué zona?
3. ¿Scheduled coordina clúster?
4. ¿Qué es idempotencia?
5. ¿Cómo manejar fallo parcial?

# 14. Checklist
- [ ] Programo conscientemente.
- [ ] Considero duración.
- [ ] Considero múltiples instancias.
- [ ] Mantengo trazabilidad.

Continúa con pruebas.
