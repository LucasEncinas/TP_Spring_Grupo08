package com.unla.grupo8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unla.grupo8.entities.Empleado;
import com.unla.grupo8.exception.ExcepcionEmpleadoEliminar;
import com.unla.grupo8.service.implementation.EmpleadoService;
import com.unla.grupo8.service.implementation.SucursalService;

import java.util.List;

@Controller
@RequestMapping("/empleado")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    private final SucursalService sucursalService;

    public EmpleadoController(EmpleadoService empleadoService, SucursalService sucursalService) {
        this.empleadoService = empleadoService;
        this.sucursalService = sucursalService;
    }

    @GetMapping("/listaEmpleados")
    public String mostrarListaEmpleados(Model model) {
        List<Empleado> empleados = empleadoService.obtenerTodos();
        model.addAttribute("empleados", empleados);
        return "empleado/listaEmpleados";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Empleado empleado = empleadoService.traerEmpleadoPorId(id);
            empleadoService.eliminarEmpleado(empleado);
            redirectAttributes.addFlashAttribute("mensajeExitoEliminar",
                    "✔️ Empleado '" + empleado.getNombre() + " " + empleado.getApellido()
                            + "' eliminado correctamente.");
        } catch (ExcepcionEmpleadoEliminar e) {
            // TODO: handle exception
            redirectAttributes.addFlashAttribute("mensajeErrorEliminar", e.getMessage());
        }
        return "redirect:/empleado/listaEmpleados";
    }

    @GetMapping("/editar/{id}")
    public String modificarEmpleado(@PathVariable Long id, Model model) {
        Empleado empleado = empleadoService.traerEmpleadoPorId(id);
        model.addAttribute("persona", empleado);
        model.addAttribute("tituloFormulario", "Modificar Empleado");
        model.addAttribute("sucursales", sucursalService.obtenerTodas());
        return "formularios/formularioRegistro";
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
