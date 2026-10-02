# Arquitectura pedagógica

## Dependencia
Curso-Java → Curso-Spring-Boot.

## Ejes
Spring/IoC → HTTP/REST → arquitectura → persistencia → integración → pruebas → operación → entrega.

## Reglas
- Java 21.
- Spring Boot 3.x.
- Inyección por constructor.
- Evitar lógica de negocio en controllers.
- No exponer entidades JPA como contrato por defecto.
- Validar en límites.
- Errores HTTP consistentes.
- Configuración fuera del código.
- No versionar secretos.
- Pruebas por responsabilidad.
- No confundir health con observabilidad completa.
- No afirmar resiliencia sin definir fallos/timeouts.
- No exponer servicios educativos directamente a Internet.
