package com.unla.grupo8.service;

import org.springframework.stereotype.Service;

import com.unla.grupo8.entities.Turno;
import com.unla.grupo8.repositories.TurnoRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TurnoService {

    private final TurnoRepository turnoRepository;

    public TurnoService(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    public List<Turno> obtenerTurnosPorPaciente(String paciente) {
        return turnoRepository.findByPaciente(paciente);
    }

    public List<Turno> obtenerTurnosEntreFechas(LocalDateTime inicio, LocalDateTime fin) {
        return turnoRepository.findByFechaHoraBetween(inicio, fin);
    }

    public List<Turno> obtenerTurnosPorEstado(String estado) {
        return turnoRepository.findByEstado(estado);
    }
}