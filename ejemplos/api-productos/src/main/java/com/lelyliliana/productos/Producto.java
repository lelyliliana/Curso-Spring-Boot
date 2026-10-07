package com.lelyliliana.productos;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity
class Producto {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Version private Long version;
 @Column(nullable=false,unique=true,length=32) private String sku;
 @Column(nullable=false,length=100) private String nombre;
 @Column(nullable=false,precision=12,scale=2) private BigDecimal precio;
 @ManyToOne(fetch=FetchType.LAZY,optional=false)
 @JoinColumn(name="categoria_id",nullable=false) private Categoria categoria;
 @Column(nullable=false) private boolean activo=true;
 protected Producto(){}
 Producto(String sku,String nombre,BigDecimal precio,Categoria categoria){actualizar(sku,nombre,precio,categoria);}
 void actualizar(String sku,String nombre,BigDecimal precio,Categoria categoria){
  if(sku==null || !sku.matches("[A-Z0-9-]{1,32}") || nombre==null || nombre.isBlank() || nombre.length()>100 || precio==null || precio.signum()<=0 || precio.scale()>2 || precio.precision()-precio.scale()>10 || categoria==null)
   throw new IllegalArgumentException("Producto inválido");
  this.sku=sku;this.nombre=nombre.strip();this.precio=precio;this.categoria=categoria;
 }
 Long getId(){return id;} Long getVersion(){return version;} String getSku(){return sku;}
 String getNombre(){return nombre;} BigDecimal getPrecio(){return precio;} Categoria getCategoria(){return categoria;}
 boolean isActivo(){return activo;}
}
