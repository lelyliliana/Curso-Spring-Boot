# Soluciones razonadas 11: Observar SQL, identidad y unidad transaccional

**Proyecto de esta unidad:** [laboratorios/jpa-productos](../laboratorios/jpa-productos/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

## Decisión de referencia

@Entity mapea Producto; @Id y @GeneratedValue usan identidad; repository genera consultas; @Transactional rodea caso de uso. save no significa que cada cambio se haya confirmado: flush envía SQL y commit confirma la unidad. readOnly es una indicación de optimización, no un sistema de permisos que impide toda escritura. @Version detecta actualizaciones concurrentes cuando la base aplica su comparación de versión.

El código completo y sus imports están en [laboratorios/jpa-productos](../laboratorios/jpa-productos/README.md). La prueba `ProductoRepositoryTest#relacionPersistida` es evidencia específica para esta frontera. Contrasta datos/headers/valores y no solo el mensaje final de Maven.

## Práctica independiente

**Enunciado:** Describe dónde se confirma la creación y por qué se usa saveAndFlush.

**Solución y justificación:** La transacción rodea la llamada al servicio administrado. saveAndFlush envía cambios y hace observar restricciones; el commit confirma después del retorno normal a través del proxy. Una prueba con mock que devuelve una entidad no demuestra ni flush ni commit. ProductoRepositoryTest y las pruebas de integración cubren esas fronteras con base real de prueba.

## Revisar tu alternativa

Contrasta la alternativa con el recorrido específico de esta unidad: [seguir datos, decisiones y rechazos](DESARROLLO.md). Explica la frontera que prueba tu evidencia.

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

## Comparación con un proceso real

Desde la raíz, después de construir la etapa, ejecuta `python scripts/recorridos.py --etapa jpa`. Observa la secuencia y comprueba qué casos complementan esta unidad. Consulta el [desarrollo específico](DESARROLLO.md) para distinguir sus garantías de las que necesitan otra evidencia.
