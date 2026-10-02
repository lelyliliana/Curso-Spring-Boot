# Unidad 02 — Configuración, properties y perfiles

## application.properties
```properties
server.port=8080
app.nombre=Mi API
```

## Variables de entorno
Útiles para valores que cambian por entorno.

## @ConfigurationProperties
Prefiere configuración tipada cuando existen varios valores relacionados.

## Perfiles
Permiten activar configuración específica:
```text
dev
test
prod
```

No conviertas perfiles en sustituto de una estrategia clara de configuración.

## Secretos
No publiques contraseñas, tokens ni credenciales en Git.

## Reto
Crea configuración tipada para nombre, límite y modo de una aplicación, con valores distintos en test.
