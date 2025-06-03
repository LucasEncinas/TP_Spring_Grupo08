package com.unla.grupo8.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "turno")
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="hora", unique=true, nullable=false, length=45)
    private LocalTime hora;
    
    @Column(name="estado", nullable=false)
    private String estado; // Ej: "pendiente", "confirmado", "cancelado"

    //relacion con cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    //relacion con empleado
    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "dia_id")
    private Dia dia;

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;
    
    // Constructor con parámetros
    public Turno(LocalTime hora, String estado) {
        this.hora = hora;
        this.estado = estado;
    }

}
