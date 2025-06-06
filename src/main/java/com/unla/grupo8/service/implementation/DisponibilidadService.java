package com.unla.grupo8.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unla.grupo8.entities.Disponibilidad;
import com.unla.grupo8.repositories.DisponibilidadRepository;

@Service
public class DisponibilidadService {

    private final DisponibilidadRepository disponibilidadRepository;

    public DisponibilidadService(DisponibilidadRepository disponibilidadRepository) {
        this.disponibilidadRepository = disponibilidadRepository;
    }
    
    public List<Disponibilidad> obtenerDisponibilidadesPorDia(Disponibilidad.Dia dia) {
        return disponibilidadRepository.findByDia(dia);
    }
    
}
