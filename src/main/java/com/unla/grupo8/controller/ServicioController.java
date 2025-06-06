package com.unla.grupo8.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unla.grupo8.entities.Servicio;
import com.unla.grupo8.service.implementation.ServicioService;

@Controller
@RequestMapping("/servicios")
public class ServicioController {

    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping("/nombre/{nombre}")
    public List<Servicio> obtenerServiciosPorNombre(@PathVariable String nombre) {
        return servicioService.obtenerServiciosPorNombre(nombre);
    }
    
}
