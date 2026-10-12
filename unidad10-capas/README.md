# Unidad 10: Capas y responsabilidades

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

**Proyecto de esta unidad:** [laboratorios/rest-memoria](../laboratorios/rest-memoria/README.md). Sigue la [ruta progresiva](../docs/RUTA_PROGRESIVA.md) para saber qué componentes aparecen en cada etapa. Los comandos se ejecutan desde la raíz del curso.

En esta etapa no existen categoriaId, version ni credenciales. Si el enunciado menciona esos campos, resuelve primero el caso REST y completa esa comparación al llegar a JPA/integración.

## Qué aprenderás
Separar HTTP, casos de uso, dominio y persistencia sin convertir la arquitectura en carpetas vacías.

# 1. El problema

Un controller que valida reglas, calcula, consulta JPA, llama servicios externos y construye respuestas funciona al principio, pero concentra demasiadas razones de cambio.

# 2. Una estructura útil

```text
HTTP
 ↓
Controller
 ↓
Service / caso de uso
 ↓
Repository / adaptadores
 ↓
infraestructura
```

No es una ley universal.

# 3. Controller

Conoce HTTP: request, status, headers y DTO.

No debería conocer detalles SQL/JPA.

# 4. Service

Coordina casos de uso como:
```text
crear producto
cancelar pedido
registrar pago
```

Puede aplicar reglas, coordinar repositorios y delimitar transacciones.

No debe convertirse en el lugar donde ponemos todo lo que no cabe en controller.

# 5. Repository

Abstrae operaciones de persistencia necesarias.

Spring Data puede implementar interfaces, pero las consultas y su costo siguen siendo responsabilidad del diseño.

# 6. Dominio

Representa conceptos/reglas sin depender innecesariamente de HTTP.

En proyectos pequeños puede haber compromisos entre pureza y simplicidad. Hazlos conscientemente.

# 7. Dirección de dependencias

Evita que service retorne `ResponseEntity` o que el dominio conozca controllers.

La lógica central no debería depender del transporte.

# 8. Transacciones

La unidad transaccional suele corresponder a un caso de uso de servicio, no a cada llamada repository aislada.

# 9. ¿Cuántas capas?

No crees Controller→Facade→Manager→Service→Helper→Repository sin responsabilidades reales.

Más capas no significa mejor arquitectura.

# 10. Práctica guiada

Para crear producto asigna:
- parseo HTTP;
- validación DTO;
- regla SKU único;
- construcción;
- persistencia;
- respuesta 201.

Justifica dónde vive cada paso.

# 11. Errores frecuentes
- service como cajón de sastre;
- repository retornando ResponseEntity;
- controller con transacciones;
- capas por moda;
- dominio acoplado a HTTP.

# 12. Reto
Refactoriza un controller monolítico y prueba la lógica sin levantar servidor.

# 13. Autoevaluación
1. ¿Qué conoce controller?
2. ¿Qué coordina service?
3. ¿Repository es solo una carpeta?
4. ¿Dónde suele vivir la unidad transaccional?
5. ¿Más capas es mejor?

# 14. Checklist
- [ ] Separo fronteras.
- [ ] Mantengo casos de uso.
- [ ] Evito capas vacías.
- [ ] Pruebo lógica sin HTTP.

Continúa con JPA.


---

## Caso desarrollado: Repartir responsabilidades

El controller transforma HTTP en una llamada de servicio; el servicio guarda y consulta el mapa con SKU único; el DTO define JSON y el advice traduce excepciones. synchronized protege una JVM; en la siguiente etapa la base añade UNIQUE y transacciones.

### Ejecutar y comprender

1. Prepara el [entorno de tu sistema](../docs/ENTORNO.md).
2. Sigue el [laboratorio completo](LABORATORIO.md), que identifica código, prueba y resultado.
3. Ejecuta desde la raíz:

```text
mvn -f laboratorios/rest-memoria/pom.xml "-Dtest=ProductoRestTest#actualizarRechazaSkuAjeno" test
```

4. Resuelve la [práctica](PRACTICA.md).
5. Compara después con las [soluciones razonadas](SOLUCIONES.md).

### Reto explicado

Ubica la comprobación SKU, el UNIQUE y el 409 en capas y explica la carrera.

El objetivo es justificar una decisión con evidencia. No necesitas memorizar todas las anotaciones del proyecto avanzado para estudiar esta unidad.

## Continuar el curso

- **Unidad anterior:** [Unidad 09: Manejo global de errores](../unidad09-errores/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 11: Spring Data JPA y persistencia](../unidad11-jpa/README.md)
