package com.unla.grupo8.entities;

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
@Table(name = "servicio")

public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServicio;

    @Column(name = "nombre", unique = false, nullable = false, length = 45)
    private String nombre;

    @Column(name = "duracion", unique = false, nullable = false)
    private int duracion; // Duración en minutos

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;

    @ManyToMany
    @JoinTable(name = "servicio_empleado", joinColumns = @JoinColumn(name = "servicio_id"), inverseJoinColumns = @JoinColumn(name = "empleado_id"))
    private Set<Empleado> empleados;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "servicio_id")
    private List<Disponibilidad> disponibilidades;

    public Servicio(String nombre, int duracion) {
        this.nombre = nombre;
        this.duracion = duracion;
    }
}
