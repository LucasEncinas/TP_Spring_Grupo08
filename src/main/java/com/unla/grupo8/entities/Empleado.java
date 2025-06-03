package com.unla.grupo8.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "empleado")
@PrimaryKeyJoinColumn(name = "id")

public class Empleado extends Persona {
   // @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long idEmpleado;

    
    @Column(name = "Nro_legajo", unique = true, nullable = false, length = 15)
    private String legajo;

    //relacion con Turno
    @OneToMany(mappedBy = "empleado")
    private List<Turno> turnos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;

   @ManyToMany(mappedBy = "empleados")
   private Set<Servicio> servicios;


    //CONSTRUCTOR que hereda de Persona
    public Empleado(String nombre, String apellido, String dni, LocalDate fechaNacimiento, Contacto contacto, String legajo) {
        super(nombre, apellido, dni, fechaNacimiento, contacto);
        this.legajo = legajo;
    }
    
  


}
