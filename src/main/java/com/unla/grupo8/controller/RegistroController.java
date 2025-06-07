package com.unla.grupo8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.unla.grupo8.entities.Cliente;
import com.unla.grupo8.entities.Empleado;
import com.unla.grupo8.service.implementation.ClienteService;
import com.unla.grupo8.service.implementation.EmpleadoService;

@Controller
@RequestMapping("/formularios")
public class RegistroController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping("/formularioRegistro")
    public String mostrarFormularioRegistro() {
        return "formularios/formularioRegistro";
    }

    @PostMapping("/guardar")
    public String guardarPersona(
                              @RequestParam("nombre") String nombre,
                              @RequestParam("apellido") String apellido,
                              @RequestParam("dni") String dni,
                              @RequestParam("fechaNacimiento") String fechaNacimiento,
                              @RequestParam("tipo") String tipo, // "cliente" o "empleado"
                              RedirectAttributes redirectAttributes) {
    //Casteo de fecha de nacimiento
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter);
    // Verificamos si es cliente o empleado
    if (tipo.equals("cliente")) {
        // Creaamos y guardamos cliente
        Cliente nuevoCliente = new Cliente(nombre, apellido, dni, fechaNac, null, "2");
        clienteService.guardarCliente(nuevoCliente); // Guarda en MySQL
        redirectAttributes.addFlashAttribute("mensaje", "Cliente guardado correctamente");
        return "redirect:/formularios/formularioRegistro";
    } else if (tipo.equals("empleado")) {
        // Creamos y guardamos empleado
        Empleado nuevoEmpleado = new Empleado(nombre, apellido, dni, fechaNac, null, "AAA174");
        empleadoService.guardarEmpleado(nuevoEmpleado); // Guarda en MySQL
        redirectAttributes.addFlashAttribute("mensaje", "Empleado guardado correctamente");
        return "redirect:/formularios/formularioRegistro";
    }
    // Si no es ni cliente ni empleado, redirigimos con mensaje de error
    redirectAttributes.addFlashAttribute("error", "Tipo de persona no válido");
    return "redirect:/formularios/formularioRegistro";
    }
}