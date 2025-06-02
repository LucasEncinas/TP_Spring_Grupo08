package com.unla.grupo8.entities;

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
@PrimaryKeyJoinColumn(name = "id")

public class Cliente extends Persona {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long idCliente;


    @OneToMany(mappedBy = "cliente")
    private List<Turno> turnos = new ArrayList<>();

     
    // Constructor que hereda de Persona
    public Cliente(String nombre, String apellido, String dni, Contacto contacto) {
        super(nombre, apellido, dni, contacto);
    }
    
}
