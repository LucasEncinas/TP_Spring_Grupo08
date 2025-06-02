package com.unla.grupo8.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalTime;
import java.util.List;

import com.unla.grupo8.entities.Turno;

public interface TurnoRepository extends JpaRepository<Turno, Long> {

    List<Turno> findByHora(LocalTime hora);

    List<Turno> findByEstado(String estado);
}
