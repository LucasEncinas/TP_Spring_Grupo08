package com.unla.grupo8.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "empleado")

@PrimaryKeyJoinColumn(name = "idEmpleado")

public class Empleado extends Persona {

    @Column(name = "Nro_legajo", unique = true, nullable = false, length = 15)
    private String legajo;

    @OneToMany(mappedBy = "empleado")
    @JsonBackReference("turno-empleado")
    private List<Turno> turnos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    @JsonBackReference("sucursal-empleados")
    private Sucursal sucursal;

    @ManyToMany(mappedBy = "empleados", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JsonBackReference("empleado-servicio")
    private Set<Servicio> servicios;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "rol", nullable = false)
    private String rol; // Rol del usuario (ej: "EMPLEADO")

    // CONSTRUCTOR que hereda de Persona
    public Empleado(String nombre, String apellido, String dni, LocalDate fechaNacimiento, Contacto contacto,
            String legajo) {
        super(nombre, apellido, dni, fechaNacimiento, contacto);
        this.legajo = legajo;
    }

}
