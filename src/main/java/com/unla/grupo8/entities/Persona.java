package com.unla.grupo8.entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor

@Table(name = "persona")
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPersona")
    private Long idPersona;

    @Column(name="nombre", nullable=false, length=45)
    private String nombre;

    @Column(name="apellido", nullable=false, length=45)
    private String apellido;

    @Column(name="dni", unique=true, nullable=false, length=15)
    private String dni;

    @Column(name="fecha_nacimiento", unique = true, nullable=false)
    private LocalDate fechaNacimiento;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contacto_id")
    private Contacto contacto;

    // Constructor con referencia a Contacto
    public Persona(String nombre, String apellido, String dni, LocalDate fechaNacimiento, Contacto contacto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.contacto = contacto;
    }

}
