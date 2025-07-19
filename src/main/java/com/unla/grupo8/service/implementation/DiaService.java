package com.unla.grupo8.service.implementation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        if (dia.getFecha().isBefore(LocalDate.now())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaFormateada = dia.getFecha().format(formatter);
            throw new RuntimeException("❌ La fecha seleccionada (" + fechaFormateada
                    + ") ya ha vencido.\nPor favor, ingresa una fecha válida.");
        }
        diaRepository.save(dia);
    }

    public List<Dia> obtenerDiasPorFecha(LocalDate fecha) {
        return diaRepository.findByFecha(fecha);
    }

    public List<Dia> obtenerDiasPorSucursal(Long idSucursal) {
        return diaRepository.findBySucursalIdSucursal(idSucursal);
    }

    public void eliminarDia(Long id) {
        diaRepository.deleteById(id);
    }

    public Dia obtenerPorId(Long eventoId) {
        return diaRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Día no encontrado con ID: " + eventoId));
    }

    public Dia buscarPorId(Long id) {
        return diaRepository.findById(id).orElse(null);
    }

}
