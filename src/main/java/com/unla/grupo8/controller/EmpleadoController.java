package com.unla.grupo8.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.unla.grupo8.entities.Turno;
import com.unla.grupo8.service.implementation.EmpleadoService;
import com.unla.grupo8.service.implementation.TurnoService;

import java.util.List;



@Controller
@RequestMapping("/empleado")
public class EmpleadoController {

    private final EmpleadoService empleadoService;
    @Autowired
    private final TurnoService turnoService;

    public EmpleadoController(EmpleadoService empleadoService, TurnoService turnoService) {
        this.empleadoService = empleadoService;
        this.turnoService = turnoService;
    }

    //@GetMapping("/index")
    //public String mostrarVistaEmpleado() {
    //    return "empleado/index"; // 
    //}

    @GetMapping("/index")
    public String verTurnos(Model model) {
        List<Turno> turnos = turnoService.obtenerTodos(); // Obtener desde BD
        model.addAttribute("turnos", turnos);
        return "empleado/index";
    }

    

    // traer todos los empleados
    // @GetMapping
    // public ResponseEntity<List<Empleado>> traerTodosLosEmpleados() {
    // return ResponseEntity.ok(empleadoService.traerTodosLosEmpleados());
    // }

    // traer empleado por ID
    // @GetMapping("/{id}")
    // public ResponseEntity<Empleado> traerEmpleadoPorId(@PathVariable Long id) {
    // Optional<Empleado> empleado = empleadoService.traerEmpleadoPorId(id);
    // return empleado.map(ResponseEntity::ok).orElseGet(() ->
    // ResponseEntity.notFound().build());
    // }

}
