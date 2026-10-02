package com.lelyliliana.productos;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
record ProductoRequest(@NotBlank String nombre,@NotNull @Positive BigDecimal precio){}
record ProductoResponse(Long id,String nombre,BigDecimal precio){}
