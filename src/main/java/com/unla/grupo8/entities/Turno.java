package com.unla.grupo8.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "turno")
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTurno;

    @Column(name = "hora", nullable = false, length = 45)
    private LocalTime hora;

    @Column(name = "estado", nullable = false)
    private String estado; // Ej: "pendiente", "confirmado", "cancelado"

    // relacion con cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonManagedReference("turno-cliente")
    private Cliente cliente;

    // relacion con empleado
    @ManyToOne
    @JoinColumn(name = "empleado_id")
    @JsonManagedReference("turno-empleado")
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "dia_id")
    private Dia dia;

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    @JsonBackReference("sucursal-turnos")
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;

    public Turno(LocalTime hora, String estado) {
        this.hora = hora;
        this.estado = estado;
    }

    public String toString() {
        return "Turno{" +
                "idTurno=" + idTurno +
                ", hora=" + hora +
                ", estado='" + estado + '\'' +
                ", cliente=" + (cliente != null ? cliente.getIdPersona() : null) +
                ", empleado=" + (empleado != null ? empleado.getIdPersona() : null) +
                //", dia=" + (dia != null ? dia.getIdDia() : null) +
                ", servicio=" + (servicio != null ? servicio.getIdServicio() : null) +
                '}';
    }
}
