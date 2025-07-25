package com.unla.grupo8.controller;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.unla.grupo8.dtos.DisponibilidadCrearDTO;
import com.unla.grupo8.entities.Disponibilidad;
import com.unla.grupo8.entities.Disponibilidad.Dia;
import com.unla.grupo8.entities.Servicio;
import com.unla.grupo8.service.implementation.ServicioService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/disponibilidades")
@Tag(name = "Disponibilidad", description = "Operaciones relacionadas a la gestión de disponibilidades")
public class DisponibilidadRestController {

    @Autowired
    private ServicioService servicioService;

    @PostMapping("/guardar")
    public ResponseEntity<String> guardarDisponibilidad(@RequestBody DisponibilidadCrearDTO dto) {
        Servicio servicio = servicioService.obtenerPorId(dto.servicioId());

        if (servicio == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El servicio no existe.");
        }

        try {
            LocalTime horaInicio = LocalTime.parse(dto.horaDesde());
            LocalTime horaFin = LocalTime.parse(dto.horaHasta());

            for (String diaStr : dto.dias()) {
                Dia diaSeleccionado = Dia.valueOf(diaStr.toUpperCase());
                Disponibilidad nueva = new Disponibilidad(horaInicio, horaFin, diaSeleccionado);
                servicio.getDisponibilidades().add(nueva);
            }

            servicioService.guardar(servicio);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Disponibilidades guardadas correctamente para el servicio " + servicio.getIdServicio());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al guardar las disponibilidades: " + e.getMessage());
        }
    }
}