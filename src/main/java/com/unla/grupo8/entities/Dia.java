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
@Table(name = "dia")
public class Dia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha", unique = true, nullable = false)
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;

    public Dia(LocalDate fecha) {
        this.fecha = fecha;
    }


    
    public String toString() {
        return "Dia{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", sucursal=" + sucursal.getIdSucursal();
    }



    public String getIdDia() {
        return getIdDia();
    }

}
