package com.unla.grupo8.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.grupo8.entities.Turno;
import com.unla.grupo8.repositories.TurnoRepository;

import java.time.LocalTime;
import java.util.List;

@Service
public class TurnoService {

    @Autowired
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

    public void guardar(Turno turno) {
        if (turno.getEstado() == null || turno.getEstado().isEmpty()) {
            turno.setEstado("confirmado"); // Valor por defecto si no se envía desde el formulario
        }
        turnoRepository.save(turno);
    }

    public List<Turno> obtenerTodos() {
        return turnoRepository.findAll();
    }

    public List<Turno> obtenerTurnosConfirmados() {
        return turnoRepository.findByEstado("confirmado"); // Filtra solo los asignados
    }

}