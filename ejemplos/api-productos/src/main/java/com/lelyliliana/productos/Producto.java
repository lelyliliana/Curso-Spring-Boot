package com.lelyliliana.productos;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity
class Producto {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String nombre;
 @Column(nullable=false,precision=12,scale=2) private BigDecimal precio;
 protected Producto(){}
 Producto(String nombre,BigDecimal precio){this.nombre=nombre;this.precio=precio;}
 Long getId(){return id;} String getNombre(){return nombre;} BigDecimal getPrecio(){return precio;}
 void actualizar(String nombre,BigDecimal precio){this.nombre=nombre;this.precio=precio;}
}
