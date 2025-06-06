package com.unla.grupo8.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.unla.grupo8.entities.Empleado;
import com.unla.grupo8.service.implementation.ClienteService;
import com.unla.grupo8.service.implementation.EmpleadoService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/empleado")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }
    
     @GetMapping("/index")
    public String mostrarVistaEmpleado() {
        return "empleado/index"; // ✅ Verifica que esté en `templates/empleado/index.html`
    }

    

  //traer todos los empleados
  //   @GetMapping
   // public ResponseEntity<List<Empleado>> traerTodosLosEmpleados() {
    //    return ResponseEntity.ok(empleadoService.traerTodosLosEmpleados());
   // } 
  
    //traer empleado por ID
   // @GetMapping("/{id}")
    //public ResponseEntity<Empleado> traerEmpleadoPorId(@PathVariable Long id) {
   //     Optional<Empleado> empleado = empleadoService.traerEmpleadoPorId(id);
    //    return empleado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
   // }

}
