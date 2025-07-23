package com.unla.grupo8.repositories;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unla.grupo8.entities.Disponibilidad;

public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Long> {

    List<Disponibilidad> findByDia(Disponibilidad.Dia dia);

    List<Disponibilidad> findByHoraDesde(LocalTime horaDesde);
    
    List<Disponibilidad> findByHoraHasta(LocalTime horaHasta);

    @Query("SELECT d FROM Disponibilidad d WHERE d.servicio.idServicio = :idServicio")
    List<Disponibilidad> findByServicioId(@Param("idServicio") Long idServicio);

    List<Disponibilidad> findByServicio_IdServicio(Long idServicio);

    List<Disponibilidad> findByServicio_IdServicioAndDia(Long servicioId, Disponibilidad.Dia dia);

    @Query("SELECT d.horaDesde FROM Disponibilidad d WHERE d.servicio.idServicio = :servicioId")
    List<LocalTime> findHorariosByServicioId(@Param("servicioId") Long servicioId);
}

