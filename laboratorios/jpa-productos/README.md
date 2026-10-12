# Etapa 2: JPA y migraciones (unidades 11–14)

El catálogo ahora guarda productos/categorías en una base y añade version para impedir actualizaciones silenciosas. Conserva DTO, capas y errores de la etapa REST. No incluye Spring Security, proveedor externo, Actuator ni pedidos. Usa este proyecto local mientras estudias persistencia.

## Paso 1: probar

Desde la raíz del curso:

```text
mvn -f laboratorios/jpa-productos/pom.xml verify
```

Las pruebas aplican migraciones y comprueban FK, UNIQUE, paginación y ciclo CRUD. En [FlujoIntegrationTest](src/test/java/com/lelyliliana/productos/FlujoIntegrationTest.java) puedes seguir la versión 0 de creación y el 409 al reutilizarla después de actualizar.

## Paso 2: ejecutar sin instalar una base

Detén la etapa anterior y ejecuta:

```text
mvn -f laboratorios/jpa-productos/pom.xml spring-boot:run
```

El perfil dev usa H2 en memoria y cuatro productos de ejemplo. En otra terminal desde la raíz:

```text
python scripts/peticion.py GET http://localhost:8080/api/productos
python scripts/peticion.py POST http://localhost:8080/api/productos --json datos/producto.json --sin-csrf
```

Espera 200 y luego 201. Esta etapa todavía no pide credenciales. Al reiniciar H2 desaparecen tus modificaciones y vuelven los datos ficticios.

## Paso 3: comparar con REST

| Cambio | Dónde observarlo |
|---|---|
| Mapa a tablas | Producto, Categoria y ProductoRepository |
| Unicidad real | UNIQUE de V1, además de comprobación del servicio |
| Categoría existente | FK obligatoria y búsqueda en servicio |
| Versión | @Version y ProductoUpdateRequest |
| Unidad de trabajo | @Transactional en ProductoService |
| Evolución del esquema | V1/V2 en db/migration; V3 ficticia solo en db/dev |

En PUT envía los mismos datos de entrada, categoriaId y la version recibida. Una versión antigua devuelve 409. La entrada de POST no necesita version. La respuesta añade categoria, categoriaId, version y activo; no copies sin cambios el contrato más pequeño de la etapa REST.

## Paso 4: PostgreSQL y siguiente etapa

H2 permite practicar el modelo; no garantiza todo PostgreSQL. Para una base local de práctica, configura DB_URL, DB_USER y DB_PASSWORD externamente y ejecuta el JAR con --spring.profiles.active=prod. Sigue [operación](../../docs/OPERACION.md) para preparar la base y distinguir secretos/opciones. El artefacto de esta etapa es laboratorios/jpa-productos/target/jpa-productos.jar. La comprobación de PostgreSQL del curso también prueba esta etapa por separado.

Detén esta aplicación antes de iniciar la [API integrada](../../ejemplos/api-productos/README.md) para unidades 15–30. Ahí se incorporan proveedor, seguridad, operación y solución de pedidos.

[Ruta progresiva](../../docs/RUTA_PROGRESIVA.md) · [Unidad 11](../../unidad11-jpa/README.md)

[Contrato de esta etapa](../../docs/CONTRATO_ETAPAS.md). El argumento --sin-csrf corresponde únicamente a los servidores sin Security de estas etapas; la API integrada mantiene el token.
