# Unidad 18: Pruebas unitarias de servicios

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

## Qué aprenderás
Probar reglas/casos de uso sin levantar Spring cuando el contenedor no aporta al escenario.

# 1. Unidad bajo prueba

```java
class ProductoService {
    private final ProductoRepository repository;
}
```

Para probar reglas del service, podemos proporcionar un mock/fake del repositorio.

# 2. Sin SpringBootTest

```java
var repo = mock(ProductoRepository.class);
var service = new ProductoService(repo);
```

Esto prueba Java normal y hace visible qué dependencias necesita.

# 3. Caso normal

```text
given repo no contiene código
when crear producto
then guarda y devuelve resultado
```

Comprueba resultado y estado/colaboración relevante.

# 4. No encontrado

Si el contrato dice que buscar inexistente lanza `ProductoNoEncontradoException`, prueba exactamente esa excepción.

No pruebes un status 404 aquí: eso pertenece a la traducción HTTP.

# 5. Conflicto

Configura repo para indicar SKU existente y verifica que el service rechaza antes de guardar.

# 6. Mockito con criterio

Mockea límites como repositorios/clientes externos.

No mockees:
- Producto;
- BigDecimal;
- listas;
- DTO simples;
solo para “aislar todo”.

# 7. verify

```java
verify(repo).save(any());
```

Úsalo si guardar forma parte del comportamiento relevante.

No verifiques cada llamada privada/interna.

# 8. Captor

Puede ayudar a inspeccionar el objeto enviado al repositorio cuando esa interacción es parte del contrato.

Pero si puedes comprobar un resultado observable más claro, prefiérelo.

# 9. Clock

Para lógica temporal, inyecta `Clock` y usa un reloj fijo en test en lugar de mockear métodos estáticos de tiempo.

# 10. Práctica guiada

ProductoService:
- crear válido;
- duplicado;
- buscar existente;
- inexistente;
- actualizar precio inválido.

# 11. Errores frecuentes
- SpringBootTest para método puro;
- mockear dominio;
- probar 404 en service;
- verify de cada llamada;
- tests que replican implementación.

# 12. Reto
Suite del service que siga pasando si refactorizas internamente sin cambiar comportamiento.

# 13. Autoevaluación
1. ¿Cuándo no levantar Spring?
2. ¿Qué mockear?
3. ¿404 pertenece al service?
4. ¿Cuándo verify?
5. ¿Por qué Clock?

# 14. Checklist
- [ ] Pruebo reglas aisladas.
- [ ] Mockeo límites.
- [ ] No acoplo test a implementación.
- [ ] Mantengo tests rápidos.

Continúa con MockMvc.


---

## Caso desarrollado: Probar reglas sin cargar el contexto

ProductoServiceTest construye servicio con repositorios Mockito. Usa objetos reales de dominio y BigDecimal; no mockea valores simples. Comprueba que SKU duplicado y categoría ausente no persisten. Un test unitario no demuestra UNIQUE ni transacción real: esos riesgos van a repository/integración. No se afirma 404 en el servicio; se afirma una excepción de dominio.

### Ejecutar y comprender

1. Prepara el [entorno de tu sistema](../docs/ENTORNO.md).
2. Sigue el [laboratorio completo](LABORATORIO.md), que identifica código, prueba y resultado.
3. Ejecuta desde la raíz:

```text
mvn -f ejemplos/api-productos/pom.xml "-Dtest=ProductoServiceTest" test
```

4. Resuelve la [práctica](PRACTICA.md).
5. Compara después con las [soluciones razonadas](SOLUCIONES.md).

### Reto explicado

Añade caso válido en el dominio y un precio inválido, y justifica qué no se prueba.

El objetivo es justificar una decisión con evidencia. No necesitas memorizar todas las anotaciones del proyecto avanzado para estudiar esta unidad.

## Continuar el curso

- **Unidad anterior:** [Unidad 17: Tareas programadas y procesamiento periódico](../unidad17-tareas/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 19: Pruebas web con MockMvc](../unidad19-mockmvc/README.md)
