package com.unla.grupo8.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

import com.unla.grupo8.entities.Turno;

public interface TurnoRepository extends JpaRepository<Turno, Long> {

    List<Turno> findByPaciente(String paciente);

    List<Turno> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);

    List<Turno> findByEstado(String estado);
}
