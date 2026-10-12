# Aprender Spring Boot por etapas

Esta ruta está pensada para quien conoce Java y Maven, pero está comenzando con Spring. Estudia una unidad a la vez. En cada una: lee el concepto, ejecuta el laboratorio, modifica un caso, intenta la práctica y consulta la solución después.

## 1. Elegir el proyecto de cada tramo

| Unidades | Proyecto ejecutable | Qué incorpora | Qué puedes posponer |
|---|---|---|---|
| 00–03 | [Fundamentos](../laboratorios/fundamentos/README.md) | Servidor, configuración, contenedor y saludo | Persistencia y autenticación |
| 04–10 | [REST en memoria](../laboratorios/rest-memoria/README.md) | CRUD, filtros, DTO, validación, errores y capas | Categorías, versión, JPA y seguridad |
| 11–14 | [JPA y migraciones](../laboratorios/jpa-productos/README.md) | Categorías, relaciones, transacciones, H2/PostgreSQL y versión | Proveedor, pedidos y seguridad |
| 15–28 | [API integrada](../ejemplos/api-productos/README.md) | Integración, pruebas, seguridad y operación | Pedidos hasta el taller |
| 29–30 | [Solución de pedidos](PEDIDOS.md) | Detalles, estados, precio histórico y concurrencia | Pagos e inventario físico |

Las etapas son proyectos independientes, no perfiles que escondan código de temas posteriores. Hay repetición deliberada del catálogo REST/JPA para comparar el cambio de responsabilidad. No copies clases entre sus paquetes ni combines sus pom.xml. Usa la carpeta exacta de cada comando.

## 2. Primer recorrido sin saltos

### Primero: servidor y configuración

Prepara [entorno](ENTORNO.md). Ejecuta fundamentos y consulta el saludo. Cambia nombre, prefijo y puerto. Debes poder distinguir error de compilación, error de arranque y 404.

### Después: una petición de creación

En la etapa REST, crea un producto. Observa 201, Location y cuerpo JSON. Repite el SKU y explica el 409. Envía un precio con tres decimales y explica el 400. Sigue el recorrido controller → servicio → mapa → DTO de respuesta.

### Después: los mismos productos en una base

En JPA, compara la entrada y respuesta: ahora existen categoriaId y version. Localiza la FK y UNIQUE en la migración. Actualiza con version 0, observa version 1 y vuelve a enviar 0. La segunda operación debe rechazarse sin cambiar el precio.

### Después: permisos y operación

En la API integrada configura las claves locales con [entorno](ENTORNO.md). El cliente obtiene CSRF y conserva cookies. Compara 400, 401, 403 y 409: que una petición llegue al servidor no significa que haya llegado al controller.

### Por último: un pedido completo

Crea un borrador, cambia el precio del catálogo, confirma y observa cuándo se captura el precio. Cambia el catálogo otra vez: el total histórico permanece. Cancela con la versión vigente y prueba que un pedido cancelado no puede confirmarse.

## 3. Qué cambia en el contrato

| Contrato | REST en memoria | JPA | API integrada |
|---|---|---|---|
| POST producto | sku, nombre, precio | Añade categoriaId | Igual a JPA; requiere editor y CSRF |
| PUT producto | Datos sin versión | Añade version vigente | Igual a JPA; requiere editor y CSRF |
| Datos al reiniciar | Se pierden | H2 dev se reinicia; PostgreSQL conserva | Mismo comportamiento según base |
| Unicidad | Monitor/mapa de una JVM | UNIQUE en la base | UNIQUE y prueba de creación concurrente |
| Pedidos | No existen | No existen | Solución completa del taller |

Los bloques conceptuales de las lecciones ayudan a leer cada idea; los proyectos enlazados contienen paquetes, imports y código completo. Ejecuta desde la raíz del curso los comandos que incluyen -f. No hay pom.xml en la raíz.

## 4. Aprender sin mirar la solución primero

Antes de ejecutar, escribe una predicción concreta: status, valor o cambio persistido esperado. Si no coincide, lee el test y registra la causa. Cuando logres el caso normal, añade un límite y un rechazo. Las soluciones incluyen código de referencia, comandos o procedimientos verificables según el problema.

Avanza cuando puedas explicar el resultado y repetirlo desde otra terminal. Pasar un test es evidencia de su escenario, no sustituto de comprender el código.

[Comenzar](../unidad00-entorno/README.md) · [Índice](../README.md)
