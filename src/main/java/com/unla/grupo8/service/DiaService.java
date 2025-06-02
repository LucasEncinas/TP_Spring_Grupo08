package com.unla.grupo8.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.unla.grupo8.entities.Dia;
import com.unla.grupo8.repositories.DiaRepository;

@Service
public class DiaService {

    private final DiaRepository diaRepository;

    public DiaService(DiaRepository diaRepository) {
        this.diaRepository = diaRepository;
    }
    
    public List<Dia> obtenerDiasPorFecha(LocalDate fecha) {
        return diaRepository.findByFecha(fecha);
    }
    
}
