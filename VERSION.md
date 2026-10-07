# Versión 2.0

Referencia: Java 21, Spring Boot 4.1.1 y Maven. Se conservan las 31 unidades y sus rutas. Cada unidad contiene lección, laboratorio, práctica y solución razonada.

Se incorporan un proyecto de fundamentos y una API con DTO, validación, categorías, paginación, migraciones, bloqueo optimista, seguridad con CSRF, observabilidad y un cliente HTTP con límites de tiempo. Hay 52 pruebas Maven y un verificador del contrato sobre JAR, H2 y PostgreSQL. La automatización incluye Ubuntu, Windows y macOS con Java 21/25, y una ejecución de imagen Docker en Linux.

Consulta [verificación](docs/VERIFICACION.md) y los resultados del commit que estás estudiando. La base H2 ayuda a aprender; la comprobación PostgreSQL se ejecuta de forma independiente. El proyecto educativo necesita decisiones adicionales antes de exponerse en un sistema público (identidad, TLS, secretos, límites y respaldos).
