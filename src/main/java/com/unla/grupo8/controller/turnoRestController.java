package com.unla.grupo8.controller;

import org.springframework.web.bind.annotation.RestController;

import com.unla.grupo8.entities.Disponibilidad;
import com.unla.grupo8.entities.Empleado;
import com.unla.grupo8.entities.Servicio;
import com.unla.grupo8.entities.Sucursal;
import com.unla.grupo8.service.implementation.DisponibilidadService;
import com.unla.grupo8.service.implementation.EmpleadoService;
import com.unla.grupo8.service.implementation.ServicioService;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
@Tag(name = "Turno", description = "Operaciones relacionadas a la gestión de turnos")
public class turnoRestController {

    @Autowired
    private DisponibilidadService disponibilidadService;

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping("/sucursales/por-servicio/{idServicio}")
    public List<Sucursal> obtenerSucursales(@PathVariable Long idServicio) {
        Servicio servicio = servicioService.obtenerPorId(idServicio);
        return servicio != null ? servicio.getSucursales() : Collections.emptyList();
    }

    @GetMapping("/horarios/por-servicio-y-dia")
    public List<String> obtenerHorariosPorServicioYDia(@RequestParam Long idServicio,
            @RequestParam String diaTexto) {
        Disponibilidad.Dia dia = Disponibilidad.Dia.valueOf(diaTexto.toUpperCase());
        return disponibilidadService.obtenerHorariosPorServicioYDia(idServicio, dia);
    }

    @GetMapping("/empleados/por-sucursal/{idSucursal}")
    public List<Empleado> obtenerEmpleadosPorSucursal(@PathVariable Long idSucursal) {
        return empleadoService.obtenerEmpleadosPorSucursal(idSucursal);
    }

}
