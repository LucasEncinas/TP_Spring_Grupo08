package com.unla.grupo8.controller;

import org.springframework.web.bind.annotation.*;

import com.unla.grupo8.entities.Turno;
import com.unla.grupo8.service.TurnoService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private final TurnoService turnoService;

    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @GetMapping("/paciente/{nombre}")
    public List<Turno> obtenerTurnosPorPaciente(@PathVariable String nombre) {
        return turnoService.obtenerTurnosPorPaciente(nombre);
    }

    @GetMapping("/estado/{estado}")
    public List<Turno> obtenerTurnosPorEstado(@PathVariable String estado) {
        return turnoService.obtenerTurnosPorEstado(estado);
    }

    @GetMapping("/entre-fechas")
    public List<Turno> obtenerTurnosEntreFechas(@RequestParam LocalDateTime inicio, @RequestParam LocalDateTime fin) {
        return turnoService.obtenerTurnosEntreFechas(inicio, fin);
    }
}
