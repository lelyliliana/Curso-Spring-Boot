# Soluciones razonadas 11: Observar SQL, identidad y unidad transaccional

**Proyecto de esta unidad:** [laboratorios/jpa-productos](../laboratorios/jpa-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

@Entity mapea Producto; @Id y @GeneratedValue usan identidad; repository genera consultas; @Transactional rodea caso de uso. save no significa que cada cambio se haya confirmado: flush envía SQL y commit confirma la unidad. readOnly es una indicación de optimización, no un sistema de permisos que impide toda escritura. @Version detecta actualizaciones concurrentes cuando la base aplica su comparación de versión.

El código completo y sus imports están en [ejemplos/api-productos](../laboratorios/jpa-productos/README.md). La prueba `ProductoRepositoryTest#relacionPersistida` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Describe dónde se confirma la creación y por qué se usa saveAndFlush.

**Solución y justificación:** El proxy transaccional inicia antes del servicio y confirma después de que retorna sin error. saveAndFlush fuerza constraints antes de construir respuesta, pero no confirma la transacción. Un fallo de commit aún impide enviar éxito del controller. Autoinvocación de un método @Transactional no pasa por ese proxy.

## Revisar tu alternativa

Una alternativa es válida si conserva la regla, población, errores y límites establecidos. Explica qué cambió y qué riesgo cubre cada prueba. Para un cliente HTTP simulado no declares probado SQL; para H2 no declares garantizado PostgreSQL; para un benchmark local no declares capacidad de producción.

## Evidencia que debes entregar

Comando desde la raíz, resultado observado, caso de frontera y explicación de la decisión central. Para una modificación conserva antes/después; para un fallo conserva status o excepción de dominio; para operación documenta preparación y configuración sin credenciales reales.

[Práctica](PRACTICA.md) · [Laboratorio](LABORATORIO.md) · [Unidad](README.md)

## Resolución desarrollada

### Ubicar flush y commit

```java
@Transactional
ProductoResponse crear(ProductoRequest entrada) {
    // Las reglas y la búsqueda de categoría ocurren aquí.
    var producto = new Producto(entrada.sku(), entrada.nombre(),
        entrada.precio(), categoria(entrada.categoriaId()));
    return ProductoResponse.de(repo.saveAndFlush(producto));
}
```
La implementación completa incluye la comprobación SKU. saveAndFlush permite observar id y constraints durante el método; no hace commit de la transacción. El proxy confirma después de retornar y antes de que el controller envíe éxito. Un fallo provoca rollback. Consulta el test relacionPersistida y las pruebas de pedido sin transacción externa para distinguir flush de commit real.
