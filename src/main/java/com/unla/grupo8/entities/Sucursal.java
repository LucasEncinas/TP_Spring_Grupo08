package com.unla.grupo8.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sucursal")

public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nombre", unique=true, nullable=false, length=45)
    private String nombre;

    @Column(name="direccion", nullable=false)
    private String direccion; 

    @Column(name="telefono", unique=true, nullable=false, length=45)
    private String telefono;

    @Column(name="mail", unique=true, nullable=false, length=45)
    private String mail;

    
    public Sucursal(String nombre, String direccion, String telefono, String mail) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.mail = mail;
    }
}
