package com.lelyliliana.productos;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
record ProductoRequest(@NotBlank @Pattern(regexp="[A-Z0-9-]{1,32}") String sku,
 @NotBlank @Size(max=100) String nombre,@NotNull @DecimalMin("0.01") @Digits(integer=10,fraction=2) BigDecimal precio,
 @NotNull @Positive Long categoriaId){}
record ProductoUpdateRequest(@NotBlank @Pattern(regexp="[A-Z0-9-]{1,32}") String sku,
 @NotBlank @Size(max=100) String nombre,@NotNull @DecimalMin("0.01") @Digits(integer=10,fraction=2) BigDecimal precio,
 @NotNull @Positive Long categoriaId,@NotNull @PositiveOrZero Long version){}
record ProductoResponse(Long id,String sku,String nombre,BigDecimal precio,Long categoriaId,String categoria,Long version,boolean activo){
 static ProductoResponse de(Producto p){return new ProductoResponse(p.getId(),p.getSku(),p.getNombre(),p.getPrecio(),p.getCategoria().getId(),p.getCategoria().getNombre(),p.getVersion(),p.isActivo());}
}
record Pagina<T>(List<T> contenido,int pagina,int tamano,long total,int paginas){}
record CategoriaRequest(@NotBlank @Size(max=80) String nombre){}
record CategoriaResponse(Long id,String nombre){}
record Cotizacion(String sku,BigDecimal precio){}
