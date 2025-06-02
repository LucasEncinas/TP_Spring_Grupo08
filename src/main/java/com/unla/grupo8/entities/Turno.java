package com.unla.grupo8.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "turno")
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="paciente", unique=true, nullable=false, length=45)
    private String paciente;

    @CreationTimestamp
    private LocalDateTime fechaHora;
    
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
    
    // Constructor con parámetros
    public Turno(String paciente, LocalDateTime fechaHora, String estado) {
        this.paciente = paciente;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

}
