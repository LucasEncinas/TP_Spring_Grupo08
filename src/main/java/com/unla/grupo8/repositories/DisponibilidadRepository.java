package com.unla.grupo8.repositories;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unla.grupo8.entities.Disponibilidad;

public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Long> {

    List<Disponibilidad> findByDia(Disponibilidad.Dia dia);

    List<Disponibilidad> findByHoraDesde(LocalTime horaDesde);
    
    List<Disponibilidad> findByHoraHasta(LocalTime horaHasta);
    
}
