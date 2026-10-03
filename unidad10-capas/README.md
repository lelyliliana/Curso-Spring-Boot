# Unidad 10 — Capas y responsabilidades

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

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

## Continuar el curso

- **Unidad anterior:** [Unidad 09 — Manejo global de errores](../unidad09-errores/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 11 — Spring Data JPA y persistencia](../unidad11-jpa/README.md)
