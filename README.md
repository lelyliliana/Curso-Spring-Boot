# Curso de Spring Boot y APIs REST

**[Comenzar el curso: Unidad 00: Entorno y primer proyecto Spring Boot](unidad00-entorno/README.md)**

Puedes leer las lecciones aquí sin conocer GitHub. Al terminar cada unidad, usa **Siguiente unidad** para avanzar; **Unidad anterior** y **Volver al índice** te permiten regresar.

[Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

**Versión 2.2**

Curso abierto para aprender a construir **aplicaciones backend y APIs REST con Java y Spring Boot**, desde la creación del proyecto hasta persistencia, integración con servicios externos, pruebas, observabilidad, seguridad básica, rendimiento y despliegue en contenedores.

## Requisito recomendado

[Curso de Java desde cero](https://github.com/lelyliliana/Curso-Java)

Se asume manejo de Java, POO, excepciones, colecciones, Maven y pruebas básicas.

## Versión de referencia

- Java 21 LTS.
- Spring Boot 4.1.1.

Las versiones exactas de dependencias se fijan en los proyectos Maven del curso.

## Comenzar con ejemplos verificables

1. Sigue [Entorno: Ubuntu, Windows y macOS](docs/ENTORNO.md).
2. Sigue la [ruta progresiva](docs/RUTA_PROGRESIVA.md): fundamentos, REST en memoria, JPA y API integrada.
3. Consulta el [contrato HTTP](docs/CONTRATO_API.md) de la API integrada y la [solución completa de pedidos](docs/PEDIDOS.md) al llegar al taller.
4. En cada unidad realiza LABORATORIO, intenta PRACTICA y contrasta SOLUCIONES después de resolver.
5. Consulta [verificación](docs/VERIFICACION.md), [diagnóstico](docs/DIAGNOSTICO_SPRING.md), [arquitectura](docs/ARQUITECTURA.md) y [operación](docs/OPERACION.md).

Los fragmentos conceptuales de las lecciones ilustran decisiones; los cuatro proyectos Maven constituyen las implementaciones ejecutables completas de cada etapa. Las soluciones explican resultados y alternativas, no solo entregan una respuesta.

## Seguir el código y comprobar cada etapa

Las 31 unidades incluyen desarrollos que siguen archivos reales y separan garantías de framework, dominio, HTTP y base. Las soluciones distinguen el catálogo vacío de REST, los campos añadidos en JPA y los controles de la integrada.

El [índice de recorridos guiados](docs/RECORRIDOS_GUIADOS.md) enlaza los desarrollos y doce casos ejecutables sobre JAR locales aislados. Muestran peticiones, status esperado, datos posteriores y rechazos sin depender de una API pública.

## Objetivo

Al finalizar podrás:

- comprender el papel de Spring Boot y su contenedor;
- crear y configurar proyectos;
- utilizar inyección de dependencias;
- diseñar APIs REST;
- separar responsabilidades por capas;
- utilizar DTO y mapeo;
- validar entradas;
- manejar errores de forma consistente;
- persistir información con Spring Data JPA;
- modelar relaciones;
- utilizar bases de datos relacionales;
- consumir APIs externas;
- configurar timeouts y manejar fallos;
- escribir pruebas unitarias y de capa web;
- aplicar seguridad básica;
- exponer health checks y métricas;
- realizar pruebas de rendimiento;
- contenerizar una aplicación;
- construir un proyecto backend completo.

## Ruta de aprendizaje

### Nivel 1: Spring Boot
- [Unidad 00: Entorno y primer proyecto](unidad00-entorno/)
- [Unidad 01: Spring, Spring Boot y contenedor IoC](unidad01-spring-ioc/)
- [Unidad 02: Configuración, properties y perfiles](unidad02-configuracion/)
- [Unidad 03: Inyección de dependencias y componentes](unidad03-dependencias/)

### Nivel 2: APIs REST
- [Unidad 04: HTTP y diseño REST](unidad04-http-rest/)
- [Unidad 05: Controllers y endpoints](unidad05-controllers/)
- [Unidad 06: Request, ResponseEntity y códigos HTTP](unidad06-respuestas-http/)
- [Unidad 07: DTO y mapeo](unidad07-dto/)
- [Unidad 08: Validación](unidad08-validacion/)
- [Unidad 09: Manejo global de errores](unidad09-errores/)

### Nivel 3: Arquitectura y persistencia
- [Unidad 10: Capas y responsabilidades](unidad10-capas/)
- [Unidad 11: Spring Data JPA](unidad11-jpa/)
- [Unidad 12: Entidades y relaciones](unidad12-relaciones/)
- [Unidad 13: Consultas y paginación](unidad13-consultas/)
- [Unidad 14: Base de datos y migraciones](unidad14-base-datos/)

### Nivel 4: Integración
- [Unidad 15: Consumo de APIs externas](unidad15-apis-externas/)
- [Unidad 16: Timeouts, errores y resiliencia básica](unidad16-resiliencia/)
- [Unidad 17: Tareas programadas y procesamiento](unidad17-tareas/)

### Nivel 5: Pruebas
- [Unidad 18: Pruebas unitarias de servicios](unidad18-pruebas-unitarias/)
- [Unidad 19: Pruebas de controllers con MockMvc](unidad19-mockmvc/)
- [Unidad 20: Pruebas de persistencia](unidad20-pruebas-jpa/)
- [Unidad 21: Pruebas de integración](unidad21-integracion-tests/)

### Nivel 6: Operación y seguridad
- [Unidad 22: Seguridad básica con Spring Security](unidad22-seguridad/)
- [Unidad 23: Actuator y health checks](unidad23-actuator/)
- [Unidad 24: Métricas y observabilidad](unidad24-metricas/)
- [Unidad 25: Logging y trazabilidad](unidad25-logging/)
- [Unidad 26: Rendimiento y pruebas de carga](unidad26-rendimiento/)

### Nivel 7: Entrega
- [Unidad 27: Empaquetado y configuración de producción](unidad27-produccion/)
- [Unidad 28: Docker](unidad28-docker/)
- [Unidad 29: Taller de APIs](unidad29-taller/)
- [Unidad 30: Proyecto final](unidad30-proyecto-final/)

## Metodología

Cada unidad combina:
1. concepto;
2. arquitectura;
3. implementación;
4. ejemplo ejecutable;
5. contrato HTTP cuando aplica;
6. pruebas;
7. errores frecuentes;
8. diagnóstico;
9. ejercicios;
10. reto.

## Separación de alcance

Este curso enseña backend con Spring Boot. Java se estudia en Curso-Java y las interfaces web/React se mantienen como cursos independientes.

## Principio

> Una API no está bien diseñada porque responda 200: debe tener contratos claros, validar entradas, representar errores, poder probarse y poder operarse.

## Autora

**Leli Liliana Díaz Izquierdo**

Ingeniera de Sistemas · Docente investigadora · Tecnología, educación e investigación.

---

### ¿Necesitas acompañamiento personalizado?

Puedes estudiar este curso por tu cuenta. Si necesitas apoyo para configurar el entorno, comprender o resolver un error, o aplicar lo aprendido a tu tesis o proyecto, podemos trabajarlo en una sesión individual.

- **Preparar el entorno:** revisar la instalación y configuración de herramientas y dependencias.
- **Comprender y resolver errores:** analizar lo que ocurre y trabajar en una solución.
- **Aplicarlo a tu proyecto:** te acompaño a comprender el contenido y adaptarlo a tu caso.

[**Solicitar asesoría sobre este curso**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20revisando%20el%20curso%20de%20Spring%20Boot%20y%20APIs%20REST%20(lelyliliana%2FCurso-Spring-Boot)%20y%20me%20interesa%20una%20asesor%C3%ADa%20personalizada.%20Necesito%20apoyo%20con%E2%80%A6)
