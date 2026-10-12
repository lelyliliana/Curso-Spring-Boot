# Versión 2.1

Referencia: Java 21, Spring Boot 4.1.1 y Maven. Se conservan las 31 unidades y sus rutas. Cada unidad contiene lección, laboratorio, práctica y solución desarrollada.

La ruta ahora tiene cuatro proyectos Maven separados: fundamentos, REST en memoria, JPA sin seguridad y API integrada. Los comandos ejecutables indican el pom.xml o JAR desde la raíz. Las soluciones añaden pasos, ejemplos, resultados y límites de cada prueba.

La API integrada incorpora la solución completa de pedidos/detalles, estados, cantidades, precio histórico al confirmar, cancelación, migración V4 y control de versiones. V3 sigue siendo exclusivamente datos ficticios de dev. La solución no implementa pagos ni inventario físico.

La automatización ejecuta 77 pruebas Maven en las cuatro etapas con Ubuntu, Windows y macOS y Java 21/25; comprueba JAR/contratos, H2 y PostgreSQL real. Linux también prueba Docker. Consulta los resultados del commit que estás estudiando en [verificación](docs/VERIFICACION.md).
