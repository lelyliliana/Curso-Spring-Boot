# Forzar una restricción y reconocer el motor que la ejecutó

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [ejemplos/api-productos/src/test/java/com/lelyliliana/productos/ProductoRepositoryTest.java](../ejemplos/api-productos/src/test/java/com/lelyliliana/productos/ProductoRepositoryTest.java) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

@DataJpaTest configura una frontera de persistencia y sus pruebas suelen ejecutar una transacción que se revierte al terminar. La referencia importa Flyway para que el objeto de prueba sea el esquema versionado, no un DDL generado que omita las migraciones del curso.

saveAndFlush hace observar restricciones durante el caso. Un duplicado real debe fallar por UNIQUE y una relación inválida por su FK. Insertar y consultar un registro sin una regla concreta podría limitarse a repetir una capacidad del framework.

H2 en modo PostgreSQL no se convierte en PostgreSQL. Tipos, SQL, índices y bloqueo pueden diferir. El modo --postgres del verificador inicia el JAR contra bases temporales propias y consulta el historial de migraciones del motor real.

Una prueba que funcionó en un motor documenta ese escenario. La matriz CI amplía entornos, pero hay que observar sus resultados para el commit que se evalúa. No sustituyas un fallo de compatibilidad eliminando una restricción que el contrato necesita.

## 3. Contrasta el comportamiento

Ejecuta duplicadoReal y categoriaConHijosProtegida. Identifica qué sentencia provoca el rechazo y si hubo flush. Revisa el procedimiento PostgreSQL y escribe qué evidencia adicional aporta respecto a la ejecución H2.

## 4. Repite un recorrido ejecutable

Después de construir el JAR de esta etapa según [LABORATORIO.md](LABORATORIO.md), ejecuta:

```text
python scripts/recorridos.py --etapa integrado
```

El programa arranca su propio proceso local con puerto temporal y datos ficticios, comprueba resultados y lo detiene al terminar. Observa los casos de esta unidad y relaciona cada expectativa con el archivo real. Para temas de diseño o medición, el recorrido funcional complementa la actividad; no sustituye una migración, benchmark o prueba de infraestructura que no se ejecutó.

[Intentar la práctica](PRACTICA.md) · [Contrastar la solución](SOLUCIONES.md)

---

### ¿Necesitas apoyo con este tema?

Si te bloqueaste con la práctica, necesitas revisar tu entorno o adaptar lo aprendido a tu proyecto, podemos trabajarlo en una sesión personalizada.

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2020%20%E2%80%94%20Pruebas%20de%20persistencia%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad20-pruebas-jpa%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
