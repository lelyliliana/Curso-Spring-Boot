# Unidad 01 — Spring, Spring Boot y contenedor IoC

## Spring
Proporciona infraestructura para construir aplicaciones Java.

## Spring Boot
Reduce configuración repetitiva mediante auto-configuración, starters y convenciones.

## IoC
El contenedor crea y conecta objetos administrados (beans).

```java
@Service
class SaludoService {
    String saludar() { return "Hola"; }
}
```

## No es magia
El contenedor descubre/configura componentes según clases, anotaciones y configuración.

## Bean
Objeto administrado por Spring.

## Reto
Identifica qué objetos de una aplicación deberían ser administrados por Spring y cuáles pueden ser objetos de dominio normales.
