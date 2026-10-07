# Fundamentos ejecutables

Aplicación pequeña para unidades 00–03 y primer contrato HTTP. No necesita base, credenciales ni Docker. Configuración tipada, Clock inyectado, servicio, DTO y controller están en src/main; las pruebas están en src/test.

Desde raíz del curso:

```text
mvn -f laboratorios/fundamentos/pom.xml verify
mvn -f laboratorios/fundamentos/pom.xml spring-boot:run
```

GET /api/saludos?nombre=Leli responde Hola, Leli y fecha UTC. Nombre vacío o más de 40 caracteres se rechaza con 400. POST de la ruta devuelve 405. Las pruebas del servicio usan fecha fija de 2026-01-01; el servidor real usa el día actual UTC.

El prefijo se cambia con --saludo.prefijo y el puerto con --server.port. El record Saludo no es bean; el servicio y Clock sí. La aplicación se detiene con Ctrl+C y el JAR construido es target/fundamentos.jar.

[Entorno](../../docs/ENTORNO.md) · [Unidad 00](../../unidad00-entorno/README.md)
