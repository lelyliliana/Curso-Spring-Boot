# Entorno: Ubuntu, Windows y macOS

La referencia es Java 21, Spring Boot 4.1.1 y Maven 3.9.x. Boot 4.1.1 admite Java 17 como mínimo; el curso elige 21 para mantener una base común. La automatización comprueba 21 y 25. Python 3.11 o posterior sirve para los clientes de práctica y la comprobación operativa; no reemplaza Java ni es requisito de los tests Maven. PostgreSQL 17/18 se utiliza al llegar a persistencia y producción. Docker es opcional hasta Unidad 28.

## Paso 1: instalar Java y Maven

### Ubuntu

1. `sudo apt update`.
2. `sudo apt install openjdk-21-jdk maven`.
3. Comprueba `java --version` y `mvn --version`. Maven puede tener otra versión 3.9.x según distribución; el proyecto utiliza una versión compatible con Boot.
4. Si Maven reporta otro Java, revisa `update-alternatives --config java` y JAVA_HOME. No cambies archivos del proyecto para resolver una ruta incorrecta.

### Windows

1. Instala un JDK 21 desde una distribución como [Eclipse Temurin](https://adoptium.net/temurin/releases/?version=21).
2. Descarga [Maven binario](https://maven.apache.org/download.cgi), descomprime y agrega su carpeta bin al PATH. JAVA_HOME debe apuntar al directorio del JDK, no a bin.
3. Abre **una nueva ventana de PowerShell** y ejecuta `java --version` y `mvn --version`.
4. Para curl utiliza `curl.exe`, evitando el alias de versiones antiguas de PowerShell. Los clientes Python del curso evitan diferencias de comillas/cookies entre shells.

### macOS

1. Instala un JDK 21 compatible con tu arquitectura Intel o Apple Silicon (Temurin es una opción).
2. Si ya usas Homebrew: `brew install maven`. También puedes descargar el Maven binario oficial.
3. En Terminal comprueba `java --version` y `mvn --version`.
4. Si tienes varios JDK, usa `export JAVA_HOME=$(/usr/libexec/java_home -v 21)` para la sesión y revisa otra vez Maven. No supongas la misma ruta Intel/ARM.

## Paso 2: obtener el curso

Descarga ZIP desde GitHub y descomprímelo, o usa:

```text
git clone https://github.com/lelyliliana/Curso-Spring-Boot.git
```

Abre terminal en la carpeta que contiene README.md. Si descargaste ZIP puede terminar en -main. Todos los comandos de las unidades parten de esa **raíz** y usan `-f` para ubicar pom.xml.

## Paso 3: comenzar sin base ni autenticación

```text
mvn -f laboratorios/fundamentos/pom.xml verify
mvn -f laboratorios/fundamentos/pom.xml spring-boot:run
```

Conserva la segunda terminal ejecutando el servidor y en otra consulta:

```text
python scripts/peticion.py GET http://localhost:8080/api/saludos?nombre=Leli
```

Usa `python3` en Ubuntu/macOS si `python` no apunta a Python 3, y `py` en Windows si corresponde. Respuesta 200 con mensaje Hola, Leli y fecha UTC. Detén el servidor con Ctrl+C antes de iniciar otro en 8080.

## Paso 4: preparar credenciales locales para la API

El ejemplo avanzado requiere dos claves configuradas externamente. Son cuentas educativas en memoria, no un proveedor de identidad de producción. No hay contraseña predeterminada en el código principal.

Bash (Ubuntu/macOS), elige claves propias de práctica:

```bash
read -rs -p 'Clave local editor: ' APP_EDITOR_PASSWORD
export APP_EDITOR_PASSWORD
read -rs -p 'Clave local lector: ' APP_LECTOR_PASSWORD
export APP_LECTOR_PASSWORD
```

PowerShell:

```powershell
$env:APP_EDITOR_PASSWORD = Read-Host 'Clave local editor' -MaskInput
$env:APP_LECTOR_PASSWORD = Read-Host 'Clave local lector' -MaskInput
```

`-MaskInput` requiere PowerShell 7.1 o posterior. En Windows PowerShell 5.1 utiliza:

```powershell
$clave = Read-Host 'Clave local editor' -AsSecureString
$env:APP_EDITOR_PASSWORD = [System.Net.NetworkCredential]::new('', $clave).Password
$clave = Read-Host 'Clave local lector' -AsSecureString
$env:APP_LECTOR_PASSWORD = [System.Net.NetworkCredential]::new('', $clave).Password
Remove-Variable clave
```

No pongas claves en comandos que queden en historial. Variables de entorno no son una bóveda y pueden ser accesibles al proceso/plataforma.

Las pruebas Maven usan claves ficticias exclusivas de test. El proceso ejecutado necesita las tuyas. Las variables permanecen en esa terminal; otra ventana no las hereda automáticamente.

## Paso 5: ejecutar API con datos ficticios

```text
mvn -f ejemplos/api-productos/pom.xml verify
mvn -f ejemplos/api-productos/pom.xml spring-boot:run
```

El perfil dev utiliza H2 en memoria, Flyway y datos ficticios. Se reinicia el contenido al detener el proceso, no al cambiar una página del navegador. No utiliza H2 como respaldo permanente. GET /api/productos devuelve cuatro productos iniciales en contenido; GET /api/categorias devuelve tres categorías.

```text
python scripts/peticion.py GET http://localhost:8080/api/productos
python scripts/peticion.py POST http://localhost:8080/api/productos --json datos/producto.json --usuario editor
```

El cliente pide contraseña, obtiene token CSRF, conserva cookie y envía el header. Una creación válida responde 201/Location. Repetir LAB-01 responde 409 por SKU único. Prueba datos/producto-invalido.json con el mismo usuario y espera 400; no uses la falta de credenciales para probar validación de DTO.

El módulo avanzado incluye temas posteriores; las unidades indican qué clase y prueba debes estudiar. No hace falta configurar PostgreSQL, proveedor externo ni Docker para las primeras prácticas de MVC.

## Paso 6: empaquetar y ejecutar

```text
mvn -f ejemplos/api-productos/pom.xml verify
java -jar ejemplos/api-productos/target/api-productos.jar
```

Usa el mismo entorno de claves y cambia puerto con `--server.port=8081` si es necesario. Al empaquetar no guardas secretos en el JAR. Para PostgreSQL/producción sigue [OPERACION.md](OPERACION.md), no apuntes una práctica a una base real.

## Diagnóstico

| Síntoma | Qué revisar |
|---|---|
| Java release 21 no soportado | JDK efectivo de Maven, JAVA_HOME |
| no existe pom.xml | carpeta actual y argumento -f |
| no se resuelve dependencia | red, proxy institucional y certificado de confianza; no deshabilitar TLS |
| aplicación no inicia | propiedad requerida, validación, puerto o migración (lee causa raíz) |
| 404 | ruta, método y recurso; servidor probablemente respondió |
| 400 | forma JSON, tipo, validación o parámetros |
| 401/403 | identidad, authority y CSRF; pueden ocurrir antes de MVC |
| LazyInitializationException | mapeo fuera de transacción y estrategia de fetch |
| Flyway checksum | no editar migración aplicada; crear nueva y revisar historial |

[Índice](../README.md) · [Contrato](CONTRATO_API.md) · [Verificación](VERIFICACION.md)
