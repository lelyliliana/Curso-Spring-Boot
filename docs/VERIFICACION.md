# Comprobar el curso

## 1. Documentación y pruebas

Desde la raíz, con Java 21, Maven y Python 3.11 o superior:

```text
python scripts/verificar.py
```

En Ubuntu/macOS puedes usar python3. El programa revisa enlaces locales y los cuatro archivos de cada una de las 31 unidades; ejecuta mvn verify en ambos proyectos, lee informes de pruebas y arranca los JAR en puertos locales temporales. Usa claves ficticias generadas durante la ejecución, conserva cookies y obtiene el token CSRF antes de escribir. Detiene los procesos al terminar.

Mockito se carga mediante un agente declarado en Surefire, en lugar de depender de adjuntarlo dinámicamente durante la ejecución. El parent de Spring Boot administra su versión y el agente solo pertenece al proceso de pruebas. No lo agregues al comando del JAR de producción.

Las 52 pruebas Maven cubren configuración, servicios, validación, controllers, repositorios, seguridad, integración y respuestas de un proveedor simulado en un servidor HTTP local. No dependen de una API pública ni de credenciales reales.

La comprobación HTTP adicional prueba creación/consulta/actualización/eliminación, 201 y Location, validación, permisos, CSRF, paginación, 409 por versión antigua, métricas protegidas, salud y fallo controlado del proveedor. Los archivos de salidas contienen registros y resultado.json; están excluidos de Git.

## 2. PostgreSQL real

Instala PostgreSQL local y las dependencias del verificador:

```text
python -m pip install -r requirements.txt
python scripts/verificar.py --skip-build --postgres
```

Antes configura PGHOST, PGPORT y PGUSER para una identidad administrativa de tu base local de práctica; suministra PGPASSWORD externamente si tu autenticación lo exige. Ejemplo de parámetros sin claves: PGHOST=127.0.0.1, PGPORT=5432. --skip-build reutiliza informes y JAR del último mvn verify; omítelo cuando hayas modificado código.

El programa permite solo un servidor PostgreSQL local. Crea un rol y una base con nombres aleatorios, aplica V1/V2 con el mismo JAR en perfil prod, comprueba datos/migraciones y enfrenta dos creaciones del mismo SKU. Al terminar elimina exclusivamente el rol y la base que creó. No lo dirijas a tu base personal ni a una base de producción. Una terminación forzada del sistema puede impedir la limpieza; revisa los nombres curso_check_ si ocurrió.

## 3. Automatización en tres sistemas

[Workflow](../.github/workflows/verificar.yml): seis combinaciones de Ubuntu/Windows/macOS y Java 21/25. Cada una compila, ejecuta las pruebas y verifica el JAR con H2 y PostgreSQL 17. Ubuntu con Java 21 también construye y ejecuta la imagen Docker, contra otra base temporal. El argumento --docker utiliza red host en Linux para esta prueba; para práctica multiplataforma se utiliza Compose según [operación](OPERACION.md).

Consulta los [resultados de GitHub Actions](https://github.com/lelyliliana/Curso-Spring-Boot/actions) del commit correspondiente. Los logs y resultado.json se conservan como artefactos. Una ejecución verde prueba esos escenarios y versiones; nuevos contratos necesitan nuevas comprobaciones.

## 4. Si algo falla

Lee primero el informe Maven o el log salidas del proceso fallido. Comprueba Java/Maven, disponibilidad del puerto/base y variables de entorno. No desactives validación, CSRF o migraciones para obtener una ejecución verde. Sigue [diagnóstico](DIAGNOSTICO_SPRING.md).
