package com.unla.grupo8.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "contacto")
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="email", unique=true, nullable=false, length=45)
    private String email;

    @Column(name="telefono", nullable=false)
    private String telefono; 

    @Column(name="direccion", unique=true, nullable=false, length=45)
    private String direccion;

    // Constructor sin referencia a Persona
    public Contacto(String email, String telefono, String direccion) {
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public Object getPersona() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPersona'");
    }
}

