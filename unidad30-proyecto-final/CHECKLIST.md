# Checklist del proyecto final

- [ ] Java 21 y Spring Boot 4.1.1; versiones declaradas en Maven.
- [ ] Contrato por endpoint: métodos, entradas/salidas, permisos y errores.
- [ ] Controllers, DTO y reglas con responsabilidades explicadas.
- [ ] Validación de límites, reglas de negocio y restricciones de base.
- [ ] Relaciones sin exponer entidades ni referencias JSON circulares.
- [ ] Transacciones y conflictos de versión probados.
- [ ] Migraciones nuevas y compatibilidad PostgreSQL real.
- [ ] Configuración externa y ausencia de claves/tokens/datos privados en Git.
- [ ] Pruebas unitarias, web, repositorio e integración con casos negativos.
- [ ] Pruebas HTTP del JAR y ausencia de cambios parciales ante rechazo.
- [ ] Autenticación, permisos y política CSRF documentados/probados.
- [ ] Health mínimo, readiness y métricas protegidas.
- [ ] Logs correlacionados y sin secretos.
- [ ] Timeouts y reintentos limitados con sus supuestos.
- [ ] Tareas y comportamiento con múltiples instancias explicados.
- [ ] Medición de carga local con contexto y errores.
- [ ] JAR e imagen probados; ejecución sin usuario privilegiado.
- [ ] README reproducible para Ubuntu, Windows y macOS.
- [ ] Persistencia, actualización y respaldo/restauración descritos.
- [ ] Memoria y demostración según plantilla y rúbrica.

[Plantilla](PLANTILLA.md) · [Rúbrica](RUBRICA.md) · [Unidad](README.md)
