package com.unla.grupo8.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "persona")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nombre", nullable=false, length=45)
    private String nombre;

    @Column(name="apellido", nullable=false, length=45)
    private String apellido;

    @Column(name="dni", unique=true, nullable=false, length=15)
    private String dni;

    @OneToOne
    @JoinColumn(name = "contacto_id")
    private Contacto contacto;

    // Constructor con referencia a Contacto
    public Persona(String nombre, String apellido, String dni, Contacto contacto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.contacto = contacto;
    }
}
