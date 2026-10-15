# Ejecutar el mismo artefacto con configuración de destino

[Unidad](README.md) · [Laboratorio](LABORATORIO.md) · [Práctica](PRACTICA.md) · [Solución](SOLUCIONES.md)

## 1. Ubica el proyecto y la fuente

Sigue [ejemplos/api-productos/src/main/resources/application-prod.properties](../ejemplos/api-productos/src/main/resources/application-prod.properties) en el proyecto indicado por esta unidad. La [ruta progresiva](../docs/RUTA_PROGRESIVA.md) explica qué cambia de una etapa a otra; los comandos del laboratorio parten de la raíz del curso.

## 2. Recorre las decisiones

mvn verify construye el artefacto integrado y ejecuta las verificaciones configuradas. El JAR incluye código y recursos; URL, identidad y clave de base se resuelven mediante configuración del proceso. No recompilas un jar para cambiar una contraseña de práctica.

prod usa PostgreSQL y valida el modelo sobre migraciones. El arranque necesita una base preparada y variables externas. Una URL correcta en forma no prueba que exista conectividad, permisos ni datos requeridos. Registra el límite que falla antes de modificar el programa.

DB_PASSWORD y las claves de identidades son secretos; puerto/perfil son opciones operativas. No guardes credenciales reales en un ejemplo de propiedades ni las imprimas para comprobar enlace. Los recorridos generan identidades ficticias efímeras para sus propias aplicaciones locales.

El cierre graceful coordina trabajo del proceso durante una señal de terminación, con un tiempo configurado. Un sistema desplegado necesita también coordinación de tráfico y señales de su plataforma; el JAR por sí solo no configura un balanceador.

## 3. Contrasta el comportamiento

Escribe la lista de variables por responsabilidad y un comando de arranque sin valores secretos. Distingue un fallo de enlace de propiedades, conexión de base y migración. El mismo JAR debe poder evaluarse con entornos aislados sin modificar su código.

## 4. Repite un recorrido ejecutable

Después de construir el JAR de esta etapa según [LABORATORIO.md](LABORATORIO.md), ejecuta:

```text
python scripts/recorridos.py --etapa integrado
```

El programa arranca su propio proceso local con puerto temporal y datos ficticios, comprueba resultados y lo detiene al terminar. Observa los casos de esta unidad y relaciona cada expectativa con el archivo real. Para temas de diseño o medición, el recorrido funcional complementa la actividad; no sustituye una migración, benchmark o prueba de infraestructura que no se ejecutó.

[Intentar la práctica](PRACTICA.md) · [Contrastar la solución](SOLUCIONES.md)

---

### ¿Necesitas apoyo con este tema?

Si te bloqueaste con la práctica, necesitas revisar tu entorno o adaptar lo aprendido a tu proyecto, podemos trabajarlo en una sesión personalizada.

[**Solicitar asesoría para este tema por WhatsApp**](https://wa.me/573103618296?text=Hola%2C%20Leli.%20Estoy%20estudiando%20Unidad%2027%20%E2%80%94%20Empaquetado%20y%20configuraci%C3%B3n%20de%20producci%C3%B3n%20del%20curso%20Spring%20Boot%20y%20APIs%20REST%20%28https%3A%2F%2Fgithub.com%2Flelyliliana%2FCurso-Spring-Boot%2Ftree%2Fmain%2Funidad27-produccion%29%20y%20necesito%20una%20asesor%C3%ADa%20personalizada.%20Mi%20duda%20es%E2%80%A6)
