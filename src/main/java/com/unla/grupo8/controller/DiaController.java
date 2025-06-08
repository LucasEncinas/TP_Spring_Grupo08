package com.unla.grupo8.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unla.grupo8.entities.Dia;
import com.unla.grupo8.service.implementation.DiaService;

@Controller
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

    @PostMapping("/guardar")
    public String guardarDia(Dia dia) {
        diaService.guardarDia(dia);
        return "redirect:/empleado/index";
    }
    
}
