package com.unla.grupo8.service.implementation;

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

    public void guardarDia(Dia dia) {
        diaRepository.save(dia);
    }

    public Dia guardarDiaR(Dia dia) {
        diaRepository.save(dia);
        return dia;
    }
    
    public List<Dia> obtenerDiasPorFecha(LocalDate fecha) {
        return diaRepository.findByFecha(fecha);
    }
    
}
