package com.unla.grupo8.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.grupo8.entities.Servicio;
import com.unla.grupo8.service.ServicioService;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    public List<Servicio> obtenerServiciosPorNombre(@PathVariable String nombre) {
        return servicioService.obtenerServiciosPorNombre(nombre);
    }
    
}
