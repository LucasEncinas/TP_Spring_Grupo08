package com.unla.grupo8.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "cliente")
@PrimaryKeyJoinColumn(name = "idCliente")

public class Cliente extends Persona {

    @Column(name = "nroCliente", unique = true, nullable = false)
    private String nroCliente;

    @OneToMany(mappedBy = "cliente")
    private List<Turno> turnos = new ArrayList<>();

    // Constructor que hereda de Persona
    public Cliente(String nombre, String apellido, String dni, LocalDate fechaNacimiento, Contacto contacto, String nroCliente) {
        super(nombre, apellido, dni, fechaNacimiento, contacto);
        this.nroCliente = nroCliente;
    }
    
}
