package com.unla.grupo8.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unla.grupo8.entities.Disponibilidad;
import com.unla.grupo8.entities.Disponibilidad.Dia;
import com.unla.grupo8.service.implementation.DisponibilidadService;

@Controller
@RequestMapping("/disponibilidades")
public class DisponibilidadController {

    private final DisponibilidadService disponibilidadService;
    public DisponibilidadController(DisponibilidadService disponibilidadService) {
        this.disponibilidadService = disponibilidadService;
    }
    
    @GetMapping("/dia/{dia}")
    public List<Disponibilidad> obtenerDisponibilidadesPorDia(@PathVariable Dia dia) {
        return disponibilidadService.obtenerDisponibilidadesPorDia(dia);
    }
    
}
