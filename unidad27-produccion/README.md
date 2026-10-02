# Unidad 27 — Empaquetado y configuración de producción

## Empaquetar
```bash
mvn clean test package
```

## Configuración
Externaliza:
- URL de BD;
- credenciales;
- endpoints;
- timeouts.

## Perfil producción
No debe contener secretos versionados.

## Graceful shutdown
Considera solicitudes en curso y cierre de recursos.

## Reto
Ejecuta el mismo JAR con dos configuraciones sin recompilar.
