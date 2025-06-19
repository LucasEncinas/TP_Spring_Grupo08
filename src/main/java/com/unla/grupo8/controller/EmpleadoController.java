package com.unla.grupo8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.unla.grupo8.entities.Empleado;
import com.unla.grupo8.service.implementation.EmpleadoService;

import java.util.List;

@Controller
@RequestMapping("/empleado")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping("/listaEmpleados")
    public String mostrarListaEmpleados(Model model) {
        List<Empleado> empleados = empleadoService.obtenerTodos();
        model.addAttribute("empleados", empleados);
        return "empleado/listaEmpleados";
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
