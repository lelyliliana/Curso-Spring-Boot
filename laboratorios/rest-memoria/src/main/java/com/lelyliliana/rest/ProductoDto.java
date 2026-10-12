package com.lelyliliana.rest;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

// La entrada no permite al cliente elegir el id.
record ProductoRequest(
    @NotBlank @Pattern(regexp = "[A-Z0-9-]{1,32}") String sku,
    @NotBlank @Size(max = 100) String nombre,
    @NotNull @DecimalMin("0.01") @Digits(integer = 10, fraction = 2) BigDecimal precio
) {}

record ProductoResponse(Long id, String sku, String nombre, BigDecimal precio) {}
record Pagina<T>(List<T> contenido, int pagina, int tamano, long total, int paginas) {}
