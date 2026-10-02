# Unidad 22 — Seguridad básica con Spring Security

## Qué aprenderás
Distinguir autenticación/autorización, comprender la cadena de filtros y proteger una API sin desactivar controles por ensayo y error.

# 1. Dos preguntas

**Autenticación:** ¿quién eres?  
**Autorización:** ¿qué puedes hacer?

Un usuario autenticado puede no estar autorizado para una operación.

# 2. SecurityFilterChain

En Spring Security moderno se configura mediante bean:

```java
@Bean
SecurityFilterChain security(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll()
            .anyRequest().authenticated()
        )
        .httpBasic(Customizer.withDefaults())
        .build();
}
```

Es un ejemplo educativo; el mecanismo de autenticación real depende del sistema.

# 3. Filtros antes del controller

Una petición puede recibir 401/403 sin llegar al controller.

Por eso seguridad forma parte de la frontera HTTP.

# 4. Contraseñas

Nunca almacenes contraseñas en texto plano.

Usa un `PasswordEncoder` adecuado como BCrypt/algoritmo configurado por la aplicación.

No cifres reversible una contraseña para luego “descifrarla al iniciar sesión”: se verifica contra un hash adaptativo.

# 5. Roles/authorities

Define permisos por capacidad real.

Evita que todo se reduzca a:
```text
ROLE_ADMIN hace todo
```
si el dominio necesita permisos más finos.

# 6. CSRF

Protege frente a peticiones no deseadas que aprovechan credenciales enviadas automáticamente por el navegador, especialmente relevante en autenticación basada en cookies/sesión.

Una API stateless con credenciales en headers tiene un análisis diferente.

No desactives CSRF copiando una configuración sin entender el modelo de autenticación.

# 7. CORS

CORS controla qué orígenes de navegador pueden leer/hacer ciertas peticiones cross-origin según política.

No es autenticación ni autorización.

```text
CORS ≠ CSRF
```

# 8. 401 vs 403

401: falta/falla autenticación apropiada.  
403: identidad autenticada pero sin permiso, u otra decisión de acceso.

Configura handlers/contrato si necesitas formato consistente.

# 9. Method security

Puedes proteger casos de uso con anotaciones como `@PreAuthorize` cuando corresponda.

No disperses reglas sin decidir dónde vive la política de autorización.

# 10. Datos sensibles

No logs:
- Authorization header;
- passwords;
- tokens;
- secretos.

# 11. Práctica guiada

API:
- GET productos público;
- POST/PUT/DELETE autenticados;
- una operación solo con authority específica.

Prueba:
- anónimo;
- credencial inválida;
- autenticado sin permiso;
- autorizado.

# 12. Errores frecuentes
- permitAll para “arreglar” 403;
- desactivar CSRF sin modelo;
- CORS como seguridad de backend;
- contraseña plana;
- tokens en logs;
- roles gigantes sin criterio.

# 13. Reto
Protege lectura/escritura y documenta matriz endpoint→permiso.

# 14. Autoevaluación
1. ¿Autenticación/autorización?
2. ¿Security filters ocurren antes del controller?
3. ¿Cómo almacenar contraseña?
4. ¿CORS=CSRF?
5. ¿401/403?
6. ¿Por qué no permitAll para depurar?

# 15. Checklist
- [ ] Defino identidad/permisos.
- [ ] Protejo contraseñas.
- [ ] Comprendo CSRF/CORS.
- [ ] Pruebo acceso.

Continúa con operación/observabilidad.
