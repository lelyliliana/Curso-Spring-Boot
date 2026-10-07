package com.lelyliliana.productos;
import java.net.URI;
import java.time.Duration;
import jakarta.validation.constraints.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
@Validated
@ConfigurationProperties("app")
record AppProperties(@NotBlank String nombre,@NotBlank String editorPassword,@NotBlank String lectorPassword,
 @NotNull URI proveedorUrl,@NotNull Duration connectTimeout,@NotNull Duration readTimeout,
 @Min(1) @Max(2) int intentos){
 @AssertTrue(message="El proveedor debe usar http o https")
 public boolean isProveedorValido(){return proveedorUrl!=null && proveedorUrl.getHost()!=null && ("http".equals(proveedorUrl.getScheme()) || "https".equals(proveedorUrl.getScheme()));}
 @AssertTrue(message="Timeouts entre 1 ms y 10 s")
 public boolean isTimeoutValido(){return connectTimeout!=null && readTimeout!=null && connectTimeout.toMillis()>0 && readTimeout.toMillis()>0 && connectTimeout.compareTo(Duration.ofSeconds(10))<=0 && readTimeout.compareTo(Duration.ofSeconds(10))<=0;}
}
