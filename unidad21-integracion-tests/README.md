# Unidad 21 — Pruebas de integración

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

## Qué aprenderás
Comprobar colaboración real entre capas y elegir el menor alcance que cubra el riesgo.

# 1. Qué integra

Ejemplo:
```text
HTTP → controller → service → JPA → PostgreSQL
```

Una prueba de integración verifica fronteras reales que las unitarias/slices sustituyen.

# 2. SpringBootTest

```java
@SpringBootTest
class FlujoTest { ... }
```

Carga contexto amplio.

No lo uses para todo: tarda más y un fallo puede ser menos localizado.

# 3. Servidor

Puedes usar contexto sin servidor real o un puerto aleatorio cuando quieras probar red/stack HTTP completo.

Elige según riesgo.

# 4. Base

La prueba debe iniciar desde estado conocido.

Opciones:
- rollback transaccional cuando aplica;
- limpieza;
- dataset;
- contenedor efímero.

No dependas de una BD de desarrollo llena de datos manuales.

# 5. Testcontainers

Puede levantar PostgreSQL real en contenedor durante tests.

Ventaja: fidelidad y aislamiento.

Costo: Docker/tiempo/infraestructura.

No necesitas Testcontainers para cada prueba unitaria.

# 6. Migraciones

Una integración valiosa:
```text
contenedor PostgreSQL
→ Flyway
→ contexto
→ flujo
```

comprueba que migraciones y mapeo realmente conviven.

# 7. Pirámide práctica

Usa:
- muchas pruebas rápidas donde aportan;
- slices para fronteras;
- integración para riesgos entre componentes;
- pocas E2E costosas.

No persigas proporciones dogmáticas.

# 8. Práctica guiada

Flujo:
1. POST producto;
2. verifica 201;
3. consulta DB/API;
4. GET;
5. comprueba datos.

# 9. Errores frecuentes
- SpringBootTest para método simple;
- base compartida;
- test dependiente de orden;
- contexto enorme sin necesidad;
- integración sin migraciones cuando ese es el riesgo.

# 10. Reto
Prueba creación→persistencia→consulta con PostgreSQL efímero si el entorno lo permite.

# 11. Autoevaluación
1. ¿Qué integra?
2. ¿Cuándo servidor real?
3. ¿Por qué DB aislada?
4. ¿Qué aporta Testcontainers?
5. ¿Más integración siempre mejor?

# 12. Checklist
- [ ] Elijo alcance.
- [ ] Aíslo estado.
- [ ] Pruebo fronteras reales.
- [ ] Mantengo suite sostenible.

Continúa con seguridad.


---

## Continuar el curso

- **Unidad anterior:** [Unidad 20 — Pruebas de persistencia JPA](../unidad20-pruebas-jpa/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 22 — Seguridad básica con Spring Security](../unidad22-seguridad/README.md)
