# Unidad 15: Consumo de APIs externas

[Volver al índice del curso](../README.md) · [Ver el curso en Aprende con Leli](https://lelyliliana.github.io/aprende-con-leli/cursos/spring-boot/)

## Qué aprenderás
Aislar dependencias HTTP externas, validar contratos y traducir sus modelos/errores al lenguaje interno.

# 1. Nueva frontera

```text
Controller
  ↓
Service
  ↓
ClienteExterno
  ↓ HTTP
API de tercero
```

No mezcles llamadas HTTP externas directamente con reglas de dominio.

# 2. Cliente

Spring ofrece opciones como `RestClient` y `WebClient`; otras APIs pueden existir según stack/versión.

Para una aplicación MVC síncrona sencilla, un cliente síncrono puede ser suficiente.

No elijas WebClient solo porque “es moderno”.

# 3. Modelo externo

Respuesta del proveedor:

```json
{"temp_c":31.2,"station":"ABC"}
```

Crea DTO externo y tradúcelo:

```text
ProveedorResponse → MedicionInterna
```

Así el proveedor no invade tu dominio.

# 4. Status

Trata explícitamente:
- 2xx;
- 4xx;
- 5xx;
- cuerpo inválido;
- campos ausentes;
- timeout.

Un 404 del proveedor no necesariamente significa 404 de tu API. Traduce semántica.

# 5. Headers/autenticación

Tokens/API keys deben provenir de configuración segura.

No los escribas en:
- código;
- logs;
- repositorio.

# 6. Contrato cambiante

El proveedor puede:
- añadir campos;
- retirar/cambiar campos;
- cambiar límites.

Valida lo que necesitas y documenta dependencia.

# 7. Testabilidad

Define un puerto/cliente:

```java
interface ClimaClient {
    Medicion obtener(...);
}
```

Service puede probarse con fake/mock sin Internet.

# 8. Pruebas del cliente

Para comprobar serialización/status HTTP del adaptador, usa un servidor simulado/controlado apropiado en tests en vez de depender del servicio real.

# 9. Práctica guiada

Consume un endpoint de prueba/controlado:
1. DTO externo;
2. cliente;
3. mapper;
4. service;
5. simula 200/404/500/cuerpo inválido.

# 10. Errores frecuentes
- HTTP externo en controller/service mezclado;
- reutilizar DTO externo como dominio;
- tests contra Internet;
- secretos hardcodeados;
- asumir siempre JSON válido.

# 11. Reto
Adaptador externo completamente sustituible con traducción de errores documentada.

# 12. Autoevaluación
1. ¿Por qué adaptador?
2. ¿DTO externo = interno?
3. ¿404 externo = 404 propio siempre?
4. ¿Dónde guardar API key?
5. ¿Por qué no tests dependientes de Internet?

# 13. Checklist
- [ ] Aíslo proveedor.
- [ ] Traduzco modelos.
- [ ] Manejo status/formato.
- [ ] Pruebo sin red real.

Continúa con resiliencia.


---

## Caso desarrollado: Consumir una API con límite y contrato propio

ProveedorClient usa RestClient y un HttpClient con tiempos de conexión/lectura. La URL base es configuración, el SKU restringido forma solo un segmento y el proveedor se simula localmente en pruebas. Un 200 con JSON del tipo esperado todavía requiere validar SKU y precio. No se llama Internet para que una prueba de tu aplicación pase.

### Ejecutar y comprender

1. Prepara el [entorno de tu sistema](../docs/ENTORNO.md).
2. Sigue el [laboratorio completo](LABORATORIO.md), que identifica código, prueba y resultado.
3. Ejecuta desde la raíz:

```text
mvn -f ejemplos/api-productos/pom.xml "-Dtest=ProveedorClientTest#contratoValido" test
```

4. Resuelve la [práctica](PRACTICA.md).
5. Compara después con las [soluciones razonadas](SOLUCIONES.md).

### Reto explicado

Traduce proveedor 404, 429, 503 y JSON inválido al contrato local.

El objetivo es justificar una decisión con evidencia. No necesitas memorizar todas las anotaciones del proyecto avanzado para estudiar esta unidad.

## Continuar el curso

- **Unidad anterior:** [Unidad 14: Base de datos real y migraciones](../unidad14-base-datos/README.md)
- **Volver al índice:** [Todas las unidades](../README.md)
- **Siguiente unidad:** [Unidad 16: Timeouts, retries y resiliencia básica](../unidad16-resiliencia/README.md)
