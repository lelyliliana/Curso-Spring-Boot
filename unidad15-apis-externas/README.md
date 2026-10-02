# Unidad 15 — Consumo de APIs externas

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
