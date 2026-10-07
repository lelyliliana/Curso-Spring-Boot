# Unidad 30: Proyecto final

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

## Propósito

Construir una API Spring Boot reproducible desde el contrato HTTP hasta su operación en contenedor.

No es obligatorio usar cada característica del curso. Cada componente debe responder a un requisito.

# Etapa 1: Problema

Elige dominio ficticio/no sensible:
- inventario;
- reservas;
- biblioteca;
- pedidos;
- sensores simulados.

Define usuario, necesidad, alcance y exclusiones.

# Etapa 2: Contrato HTTP

Antes del código documenta:
```text
método | URI | request | status | response | errores
```

Incluye al menos:
- colección;
- recurso individual;
- creación;
- actualización;
- eliminación;
- búsqueda/paginación.

# Etapa 3: Modelo y reglas

Define:
- entidades de dominio;
- invariantes;
- cardinalidades;
- estados;
- transacciones necesarias.

Dibuja modelo relacional.

# Etapa 4: Migraciones

Crea esquema con Flyway/Liquibase.

Incluye al menos dos migraciones para demostrar evolución sin destruir datos.

# Etapa 5: JPA

Mapea relaciones desde el modelo relacional.

Justifica:
- fetch;
- cascada;
- navegación;
- índices/queries.

Observa SQL.

# Etapa 6: Capas

Organiza controller, casos de uso/service, persistencia y adaptadores.

No crees capas sin responsabilidad.

# Etapa 7: DTO y validación

Separa request/response cuando el contrato lo necesite.

Distingue:
- validación de forma;
- regla de negocio.

# Etapa 8: Errores

Contrato consistente:
- 400;
- 404;
- 409;
- 500.

No expongas stack traces ni SQL.

# Etapa 9: Integración externa

Solo si aporta al dominio.

Aísla cliente y define:
- timeout;
- errores;
- traducción;
- pruebas sin Internet.

# Etapa 10: Seguridad

Define matriz endpoint→permiso.

Protege credenciales y prueba 401/403.

No se exige un sistema de identidad complejo si el proyecto no lo necesita.

# Etapa 11: Pruebas

Incluye:
- unitarias de reglas;
- web/MockMvc;
- JPA;
- integración de al menos un flujo crítico.

Usa PostgreSQL representativo para riesgos específicos de base cuando corresponda.

# Etapa 12: Observabilidad

Configura:
- health;
- métricas útiles;
- logs con contexto;
- correlation id si aporta.

No uses IDs únicos como tags de métricas.

# Etapa 13: Rendimiento

Selecciona un flujo relevante:
1. baseline;
2. carga documentada;
3. métricas/SQL;
4. hipótesis;
5. mejora;
6. repetición.

Una conclusión “no mejoró” es válida si está sustentada.

# Etapa 14: Configuración

Todo valor por entorno debe externalizarse.

No:
- contraseñas en Git;
- rutas personales;
- endpoints rígidos cuando son configuración.

# Etapa 15: Build

Debe pasar:

```bash
mvn test
mvn package
```

Documenta Java y Maven.

# Etapa 16: Docker

Construye imagen sin secretos.

Ejecuta con configuración externa y conecta servicios por red correcta.

# Etapa 17: README reproducible

Otra persona debe poder:
1. entender arquitectura;
2. preparar requisitos;
3. iniciar PostgreSQL/configuración;
4. ejecutar migraciones/app;
5. probar API;
6. ejecutar tests;
7. construir imagen.

# Etapa 18: Revisión

Usa `PLANTILLA_PROYECTO.md`, `RUBRICA.md` y `CHECKLIST.md`.

Pregúntate:
- ¿el contrato HTTP es coherente?
- ¿puedo explicar cada query?
- ¿hay N+1?
- ¿las transacciones corresponden a casos de uso?
- ¿las pruebas cubren riesgos?
- ¿hay secretos?
- ¿health/metrics/logs son seguros?
- ¿el mismo artefacto funciona con otra configuración?

# Entregables

- código Maven;
- migraciones;
- modelo;
- contrato API;
- pruebas;
- configuración de ejemplo sin secretos;
- evidencia operativa;
- Dockerfile;
- README.

# Cierre

> Una API profesional no es la que acumula anotaciones de Spring, sino la que tiene contratos claros, datos íntegros, fallos controlados, pruebas útiles y operación reproducible.


---

## Caso desarrollado: Construir una API propia justificando decisiones

La API de productos es referencia completa, no un proyecto universal. Su alcance no incluye pagos, inventario físico, login web, JWT, OAuth ni una plataforma de identidad real; Basic con cuentas en memoria es educativo. Una ampliación necesita reglas nuevas, no más anotaciones. La rúbrica exige reconstrucción, contratos, datos íntegros, pruebas y operación.

### Ejecutar y comprender

1. Prepara el [entorno de tu sistema](../docs/ENTORNO.md).
2. Sigue el [laboratorio completo](LABORATORIO.md), que identifica código, prueba y resultado.
3. Ejecuta desde la raíz:

```text
mvn -f ejemplos/api-productos/pom.xml "-Dtest=FlujoIntegrationTest" test
```

4. Resuelve la [práctica](PRACTICA.md).
5. Compara después con las [soluciones razonadas](SOLUCIONES.md).

### Reto explicado

Extiende con pedido y detalle guardando precio histórico y cuidando concurrencia.

El objetivo es justificar una decisión con evidencia. No necesitas memorizar todas las anotaciones del proyecto avanzado para estudiar esta unidad.

## Continuar el curso

- **Unidad anterior:** [Unidad 29: Taller integrador de APIs](../unidad29-taller/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)

Llegaste a la última unidad. Revisa tu proyecto y la lista de comprobación antes de dar por terminado el curso.
