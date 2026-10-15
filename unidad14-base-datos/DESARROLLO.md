# Promover esquema y configuración sin trasladar datos de demostración

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [laboratorios/jpa-productos/src/main/resources/application-prod.properties](../laboratorios/jpa-productos/src/main/resources/application-prod.properties) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

Flyway aplica cambios versionados y conserva historial. Hibernate utiliza ddl-auto=validate para contrastar el modelo con el esquema existente, en vez de crear libremente una estructura que oculte migraciones ausentes.

En JPA, V1 crea categoría/producto y sus restricciones; V2 añade activo e índice. Los datos ficticios pertenecen a la ubicación dev. En la integrada, V4 añade pedidos; producción aplica V1,V2,V4 sin insertar la V3 de demostración. La ausencia de ese número en producción es una decisión documentada de ubicaciones.

El perfil prod obtiene URL, usuario y clave externos y usa PostgreSQL. Cambiar perfil no copia la base H2 en memoria ni promueve sus registros a otra base. El mismo artefacto puede ejecutarse con configuración distinta, pero necesita una preparación de datos coherente con el destino.

Una migración aplicada no se edita para ocultar su historia. Un cambio posterior debe tener otra versión y una estrategia de actualización. Utiliza una base de práctica propia para los ensayos; los comandos del verificador crean y limpian exclusivamente sus recursos temporales.

## 3. Contrasta el comportamiento

Compara application-dev y application-prod. Enumera esquema y datos que aparecen en cada perfil. Inspecciona flyway_schema_history y explica por qué validar el mapeo no reemplaza aplicar V1/V2. En la integrada añade V4 al recorrido.

## 4. Repite un recorrido ejecutable

Después de construir el JAR de esta etapa según [LABORATORIO.md](LABORATORIO.md), ejecuta:

```text
python scripts/recorridos.py --etapa jpa
```

El programa arranca su propio proceso local con puerto temporal y datos ficticios, comprueba resultados y lo detiene al terminar. Observa los casos de esta unidad y relaciona cada expectativa con el archivo real. Para temas de diseño o medición, el recorrido funcional complementa la actividad; no sustituye una migración, benchmark o prueba de infraestructura que no se ejecutó.

[Intentar la práctica](PRACTICA.md) · [Contrastar la solución](SOLUCIONES.md)

---

### ¿Necesitas apoyo con este tema?

Si te bloqueaste con la práctica, necesitas revisar tu entorno o adaptar lo aprendido a tu proyecto, podemos trabajarlo en una sesión personalizada.

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2014%20%E2%80%94%20Base%20de%20datos%20y%20migraciones%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad14-base-datos%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
