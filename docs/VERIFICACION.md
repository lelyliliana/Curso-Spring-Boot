# Comprobar el curso

## 1. Documentación y pruebas

Desde la raíz, con Java 21, Maven y Python 3.11 o superior:

```text
python scripts/verificar.py
```

En Ubuntu/macOS puedes usar python3. El programa revisa enlaces locales y los cinco archivos de cada una de las 31 unidades (README, LABORATORIO, PRACTICA, SOLUCIONES y DESARROLLO); ejecuta mvn verify en los cuatro proyectos, lee informes de pruebas y arranca los JAR en puertos locales temporales. Usa claves ficticias generadas durante la ejecución, conserva cookies y obtiene el token CSRF antes de escribir. Detiene los procesos al terminar.

Mockito se carga mediante un agente declarado en Surefire, en lugar de depender de adjuntarlo dinámicamente durante la ejecución. El parent de Spring Boot administra su versión y el agente solo pertenece al proceso de pruebas. No lo agregues al comando del JAR de producción.

Las 77 pruebas Maven de las cuatro etapas cubren configuración, servicios, validación, controllers, repositorios, seguridad, integración, pedidos y respuestas de un proveedor simulado en un servidor HTTP local. No dependen de una API pública ni de credenciales reales.

La comprobación HTTP adicional prueba creación/consulta/actualización/eliminación, 201 y Location, validación, permisos, CSRF, paginación, 409 por versión antigua, métricas protegidas, salud y fallo controlado del proveedor. Los archivos de salidas contienen registros y resultado.json; están excluidos de Git.

También ejecuta los JAR de REST en memoria y JPA, y el ciclo de pedidos con precio histórico, cancelación y confirmaciones concurrentes. Añade los doce [recorridos guiados](RECORRIDOS_GUIADOS.md), que muestran el HTTP recibido y esperado y comprueban el cuerpo, los headers y el estado posterior. [Ruta progresiva](RUTA_PROGRESIVA.md) y [pedidos](PEDIDOS.md) explican cómo reproducirlo manualmente.

## Resultado observado de la versión 2.2

El 14 de octubre de 2026 (America/Bogota), en Linux con Amazon Corretto 21.0.12.1, Maven 3.9.16 y los proyectos Spring Boot 4.1.1 del repositorio:

| Comprobación ejecutada | Resultado |
|---|---|
| Fundamentos | 8 pruebas Maven correctas |
| REST en memoria | 7 pruebas Maven correctas |
| JPA | 7 pruebas Maven correctas |
| API integrada | 55 pruebas Maven correctas |
| Total Maven | 77; sin errores, fallos ni pruebas omitidas |
| Aplicaciones empaquetadas | Cuatro JAR construidos y arrancados por el verificador |
| Contratos HTTP operativos | Correctos con H2, incluido historial y confirmación concurrente |
| Recorridos guiados | 12 casos, 37 peticiones explícitas y 58 comprobaciones correctas |
| Documentación | 31 desarrollos presentes y enlaces locales válidos; revisión de codificación y marcas internas correcta |

Se repitieron los recorridos con variables de perfil/base ajenas en la terminal para comprobar que sus procesos descartan esa configuración y usan los datos locales del curso. Las peticiones auxiliares de arranque y CSRF no se cuentan en las 37 peticiones explícitas.

PostgreSQL y Docker no se ejecutaron en esta comprobación local. La matriz siguiente describe la automatización configurada; no afirma un resultado de CI para este cambio sin consultar su ejecución. H2 en modo PostgreSQL no demuestra compatibilidad completa con ese motor ni valida red, volumen o imagen de contenedor.

## 2. PostgreSQL real

Instala PostgreSQL local y las dependencias del verificador:

```text
python -m pip install -r requirements.txt
python scripts/verificar.py --skip-build --postgres
```

Antes configura PGHOST, PGPORT y PGUSER para una identidad administrativa de tu base local de práctica; suministra PGPASSWORD externamente si tu autenticación lo exige. Ejemplo de parámetros sin claves: PGHOST=127.0.0.1, PGPORT=5432. --skip-build reutiliza informes y JAR del último mvn verify; omítelo cuando hayas modificado código.

El programa permite solo un servidor PostgreSQL local. Crea roles y bases con nombres aleatorios: comprueba el JAR de JPA con V1/V2 y el integrado con V1/V2/V4 en perfil prod. Verifica datos/migraciones y, en el integrado, enfrenta dos creaciones del mismo SKU y dos confirmaciones del mismo pedido. Al terminar elimina exclusivamente cada rol y base que creó. No lo dirijas a tu base personal ni a una base de producción. Una terminación forzada del sistema puede impedir la limpieza; revisa los nombres curso_check_ si ocurrió.

## 3. Automatización en tres sistemas

[Workflow](../.github/workflows/verificar.yml): seis combinaciones de Ubuntu/Windows/macOS y Java 21/25. Cada una compila, ejecuta las pruebas y verifica el JAR con H2 y PostgreSQL 17. Ubuntu con Java 21 también construye y ejecuta la imagen Docker, contra otra base temporal. El argumento --docker utiliza red host en Linux para esta prueba; para práctica multiplataforma se utiliza Compose según [operación](OPERACION.md).

Consulta los [resultados de GitHub Actions](https://github.com/lelyliliana/Curso-Spring-Boot/actions) del commit correspondiente. Los logs y resultado.json se conservan como artefactos. Una ejecución verde prueba esos escenarios y versiones; nuevos contratos necesitan nuevas comprobaciones.

## 4. Si algo falla

Lee primero el informe Maven o el log salidas del proceso fallido. Comprueba Java/Maven, disponibilidad del puerto/base y variables de entorno. No desactives validación, CSRF o migraciones para obtener una ejecución verde. Sigue [diagnóstico](DIAGNOSTICO_SPRING.md).
