# Ejecutar con PostgreSQL y contenedores

## 1. Base local de práctica

Instala PostgreSQL 17/18 siguiendo el [curso SQL](https://github.com/lelyliliana/Curso-Bases-de-Datos-SQL/blob/main/docs/ENTORNO.md). Como administrador crea una identidad de práctica y establece su clave en psql con \password, sin ponerla en Git:

```sql
CREATE ROLE curso_productos LOGIN;
CREATE DATABASE curso_productos OWNER curso_productos;
```

La identidad es propietaria para ejecutar las migraciones en este laboratorio. Una producción puede separar la identidad de migración de la aplicación y conceder a ésta solo lo necesario. No necesita superusuario para consultar/escribir las tablas de su base.

## 2. Configurar el mismo JAR

Configura SPRING_PROFILES_ACTIVE=prod, DB_URL=jdbc:postgresql://localhost:5432/curso_productos, DB_USER=curso_productos y DB_PASSWORD mediante tu mecanismo local de secretos. Conserva APP_EDITOR_PASSWORD y APP_LECTOR_PASSWORD. En Bash usa export; en PowerShell $env:NOMBRE. No escribas claves reales como ejemplos versionados.

```text
java -jar ejemplos/api-productos/target/api-productos.jar
```

Flyway aplica V1 y V2; no V3 de datos dev. Comprueba las tablas, columnas, FK, UNIQUE y flyway_schema_history. Crea categoría mediante POST /api/categorias con editor y CSRF; usa el id devuelto al crear producto. No supongas que categoría 1 existe en producción vacía. Reutiliza [peticion.py](../scripts/peticion.py) con un archivo JSON ficticio.

## 3. Imagen

Con Docker instalado y ejecutándose, después de mvn verify:

```text
docker build -t curso-api-productos ejemplos/api-productos
```

La imagen contiene un JRE y target/api-productos.jar, no claves ni tu carpeta de trabajo. Ejecuta UID 10001. El tag base 21-jre debe actualizarse deliberadamente; para reproducir bytes de un release fija un digest verificado y registra arquitectura. La construcción de código no exige Docker a quien está aprendiendo las primeras unidades.

## 4. Compose (entorno ficticio separado)

Define DB_PASSWORD, APP_EDITOR_PASSWORD y APP_LECTOR_PASSWORD externamente. Compose exige esos valores; no se incluye un .env con claves. Luego:

```text
docker compose -f ejemplos/api-productos/compose.yml up --build
```

PostgreSQL escucha internamente en db:5432, sin puerto publicado al host. La API se publica en 127.0.0.1:8080. Su URL utiliza db, no localhost. El volumen datos_pg conserva datos al recrear contenedores. El healthcheck confirma que PostgreSQL acepta conexiones; no prueba tu esquema ni todas las consultas.

Para detener conservando datos:

```text
docker compose -f ejemplos/api-productos/compose.yml down
```

Eliminar el volumen elimina la base del entorno. No uses down -v como forma rutinaria de resolver una migración. Si cambias POSTGRES_PASSWORD en una base ya inicializada, la imagen no cambia automáticamente la clave de un rol existente: administra el rol de forma explícita y actualiza configuración.

## 5. Preparar un release

Pruebas correctas, contrato revisado, migraciones nuevas (no editar aplicadas), respaldo/restauración probados, identidades mínimas, secretos del entorno, TLS y límites de red, readiness y cierre coordinados. Son requisitos del sistema concreto; el proyecto educativo no configura automáticamente una plataforma pública ni un gestor de identidad.

El proveedor externo no bloquea arranque: la cotización falla de forma controlada si no está disponible. Las tareas están apagadas salvo configuración explícita. Actuator no expone entorno o beans públicamente.

[Entorno](ENTORNO.md) · [Arquitectura](ARQUITECTURA.md) · [Comprobación](VERIFICACION.md)
