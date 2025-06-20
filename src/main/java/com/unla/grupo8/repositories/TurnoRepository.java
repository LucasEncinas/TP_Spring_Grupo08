package com.unla.grupo8.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.unla.grupo8.entities.Turno;

public interface TurnoRepository extends JpaRepository<Turno, Long> {

    List<Turno> findByHora(LocalTime hora);

    List<Turno> findByEstado(String estado);

    List<Turno> findAll();

    @Query("SELECT t FROM Turno t WHERE t.dia.fecha = :fecha")
    List<Turno> buscarPorFecha(@Param("fecha") LocalDate fecha);

}
