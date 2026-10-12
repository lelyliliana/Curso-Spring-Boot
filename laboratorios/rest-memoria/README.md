# Etapa 1: REST en memoria (unidades 04–10)

Esta aplicación permite aprender controllers, DTO, validación, errores y capas con archivos completos. Solo necesita Java 21 y Maven. Comienza con un catálogo vacío; al detener el proceso se pierden los productos. Todavía no tiene categorías, versión, JPA ni autenticación.

## Paso 1: comprobar

Desde la raíz del curso (carpeta que contiene README.md):

```text
mvn -f laboratorios/rest-memoria/pom.xml verify
```

Espera BUILD SUCCESS. Lee [ProductoRestTest](src/test/java/com/lelyliliana/rest/ProductoRestTest.java): prueba un CRUD completo, validación, filtros, límites y conflictos.

## Paso 2: iniciar

```text
mvn -f laboratorios/rest-memoria/pom.xml spring-boot:run
```

Deja esa terminal abierta. Abre otra en la misma raíz y ejecuta:

```text
python scripts/peticion.py POST http://localhost:8080/api/productos --json datos/producto-memoria.json --sin-csrf
```

Espera 201, Location y un cuerpo con id, sku, nombre y precio. No se pide usuario. Repetir la petición devuelve 409. Usa python3 en Linux/macOS o py en Windows si tu instalación lo requiere.

## Paso 3: consultar

```text
python scripts/peticion.py GET "http://localhost:8080/api/productos?nombre=libro&pagina=0&tamano=2"
```

El producto aparece en contenido. La respuesta incluye pagina, tamano, total y paginas. Usa el id recibido para consultar /api/productos/{id}, no supongas que siempre será 1.

## Paso 4: entender los archivos

1. [ProductoController](src/main/java/com/lelyliliana/rest/ProductoController.java) recibe HTTP y elige status/Location.
2. [ProductoDto](src/main/java/com/lelyliliana/rest/ProductoDto.java) define la entrada validada y la respuesta.
3. [ProductoService](src/main/java/com/lelyliliana/rest/ProductoService.java) mantiene el mapa y comprueba SKU.
4. [ApiErrorHandler](src/main/java/com/lelyliliana/rest/ApiErrorHandler.java) representa rechazos con ProblemDetail.

El monitor synchronized mantiene coherente el mapa solo en este proceso. Dos réplicas tendrían catálogos distintos. La siguiente etapa traslada la garantía de unicidad a una base y añade transacciones.

## Paso 5: avanzar

Detén con Ctrl+C antes de iniciar [JPA](../jpa-productos/README.md). Ambas aplicaciones usan 8080 y son proyectos separados para que puedas inspeccionar cada etapa sin código de temas posteriores.

[Ruta progresiva](../../docs/RUTA_PROGRESIVA.md) · [Unidad 04](../../unidad04-http-rest/README.md)

[Contrato de esta etapa](../../docs/CONTRATO_ETAPAS.md). El argumento --sin-csrf corresponde únicamente a los servidores sin Security de estas etapas; la API integrada mantiene el token.
