package com.unla.grupo8.service;

import org.springframework.stereotype.Service;

import com.unla.grupo8.entities.Turno;
import com.unla.grupo8.repositories.TurnoRepository;

import java.time.LocalTime;
import java.util.List;

@Service
public class TurnoService {

    private final TurnoRepository turnoRepository;

    public TurnoService(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    public List<Turno> obtenerTurnosPorHora(LocalTime hora) {
        return turnoRepository.findByHora(hora);
    }

    public List<Turno> obtenerTurnosPorEstado(String estado) {
        return turnoRepository.findByEstado(estado);
    }
}