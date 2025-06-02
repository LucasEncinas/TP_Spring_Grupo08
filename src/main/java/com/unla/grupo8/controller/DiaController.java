package com.unla.grupo8.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.grupo8.entities.Dia;
import com.unla.grupo8.service.DiaService;

@RestController
@RequestMapping("/dias")
public class DiaController {

    private final DiaService diaService;

    public DiaController(DiaService diaService) {
        this.diaService = diaService;
    }

    @GetMapping("/dias/fecha/{fecha}")
    public List<Dia> obtenerDiasPorFecha(@PathVariable LocalDate fecha) {
        return diaService.obtenerDiasPorFecha(fecha);
    }
    
}
