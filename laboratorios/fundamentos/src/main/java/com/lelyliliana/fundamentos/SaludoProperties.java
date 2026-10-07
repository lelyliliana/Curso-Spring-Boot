package com.lelyliliana.fundamentos;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;import jakarta.validation.constraints.*;
@Validated @ConfigurationProperties("saludo")
record SaludoProperties(@NotBlank String prefijo,@Min(1) @Max(100) int longitudMaxima){}
