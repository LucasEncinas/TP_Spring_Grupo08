package com.unla.grupo8.controller;

import org.springframework.web.bind.annotation.RestController;

import com.unla.grupo8.dtos.DiaDTO;
import com.unla.grupo8.entities.Servicio;
import com.unla.grupo8.entities.Sucursal;
import com.unla.grupo8.service.implementation.DiaService;
import com.unla.grupo8.service.implementation.DisponibilidadService;
import com.unla.grupo8.service.implementation.ServicioService;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api")
public class turnoRestController {

    @Autowired
    private DisponibilidadService disponibilidadService;

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private DiaService diaService;

    @GetMapping("/sucursales/por-servicio/{idServicio}")
    public List<Sucursal> obtenerSucursales(@PathVariable Long idServicio) {
        Servicio servicio = servicioService.obtenerPorId(idServicio);
        return servicio != null ? servicio.getSucursales() : Collections.emptyList();
    }

    @GetMapping("/horarios/por-servicio/{idServicio}")
    public List<String> obtenerHorarios(@PathVariable Long idServicio) {
        return disponibilidadService.obtenerHorariosPorServicio(idServicio);
    }

    @GetMapping("/dias/por-sucursal/{idSucursal}")
    public List<DiaDTO> obtenerDias(@PathVariable Long idSucursal) {
        return diaService.obtenerDiasPorSucursal(idSucursal).stream()
                .map(d -> new DiaDTO(d.getId(), d.getFecha()))
                .collect(Collectors.toList());
    }

}
