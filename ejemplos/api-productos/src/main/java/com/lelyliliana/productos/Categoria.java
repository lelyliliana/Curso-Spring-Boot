package com.lelyliliana.productos;
import jakarta.persistence.*;
@Entity
class Categoria {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true,length=80) private String nombre;
 protected Categoria(){}
 Categoria(String nombre){this.nombre=nombre;}
 Long getId(){return id;} String getNombre(){return nombre;}
}
