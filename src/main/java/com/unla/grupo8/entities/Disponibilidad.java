package com.unla.grupo8.entities;

import java.time.LocalTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "disponibilidad")
public class Disponibilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "horaDesde", unique = false, nullable = false)
    private LocalTime horaDesde; // Hora hasta la que está disponible

    @Column(name = "horaHasta", unique = false, nullable = false)
    private LocalTime horaHasta; // Hora hasta la que está disponible

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;

    public enum Dia { // es necesario que sea un enum para poder usarlo en la base de datos
        LUNES, // no podemos llamarlo directo como enum
        MARTES,
        MIERCOLES,
        JUEVES,
        VIERNES,
        SABADO,
        DOMINGO
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "dia", unique = false, nullable = false)
    private Dia dia; // Día de la semana (Lunes, Martes, etc.)

    public Disponibilidad(LocalTime horaDesde, LocalTime horaHasta, Dia dia) {
        this.horaDesde = horaDesde;
        this.horaHasta = horaHasta;
        this.dia = dia;
    }

    public LocalTime getHoraInicio() {
    return horaDesde;
    }

    public LocalTime getHoraFin() {
    return horaHasta;
    }

    public Dia getDia (){
        return dia;
    }
}
