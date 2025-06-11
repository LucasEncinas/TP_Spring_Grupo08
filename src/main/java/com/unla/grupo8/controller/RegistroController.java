package com.unla.grupo8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.unla.grupo8.entities.Cliente;
import com.unla.grupo8.entities.Contacto;
import com.unla.grupo8.entities.Empleado;
import com.unla.grupo8.entities.Sucursal;
import com.unla.grupo8.service.implementation.ClienteService;
import com.unla.grupo8.service.implementation.ContactoService;
import com.unla.grupo8.service.implementation.EmpleadoService;
import com.unla.grupo8.service.implementation.SucursalService;

@Controller
@RequestMapping("/formularios")
public class RegistroController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private ContactoService contactoService;

    @GetMapping("/formularioRegistro")
    public String mostrarFormularioRegistro(Model model) {

        model.addAttribute("sucursales", sucursalService.obtenerTodas());
        model.addAttribute("contactos", contactoService.obtenerTodos()); // Agregamos los contactos al modelo

        return "formularios/formularioRegistro";
    }

    @PostMapping("/guardar")
    public String guardarPersona(
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("dni") String dni,
            @RequestParam("fechaNacimiento") String fechaNacimiento,
            @RequestParam("tipo") String tipo, // "cliente" o "empleado"
            @RequestParam(value = "sucursal", required = false) String idSucursal, // solo si es empleado
            @RequestParam(value = "contacto", required = false) String contacto, // opcional
            RedirectAttributes redirectAttributes) {
        // Casteo de fecha de nacimiento
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter);

        // Casteo de contacto
        Contacto contactoObj = null; // Inicializamos como null

        if (contacto != null && !contacto.isEmpty()) { // Validamos si se proporcionó un contacto
            Long idContactoAux = Long.valueOf(contacto);
            contactoObj = contactoService.obtenerPorId(idContactoAux);

            if (contactoObj == null) { // Ahora solo verificamos si hubo un ID proporcionado
                redirectAttributes.addFlashAttribute("error", "El contacto seleccionado no existe.");
                return "redirect:/formularios/formularioRegistro";
            }
        }

        long totalClientes = clienteService.contarClientes();
        long totalEmpleados = empleadoService.contarEmpleados();
        // Verificamos si es cliente o empleado
        if (tipo.equals("cliente")) {
            // Creaamos y guardamos cliente
            String nroCliente = String.valueOf(totalClientes + 1);

            Cliente nuevoCliente = new Cliente(nombre, apellido, dni, fechaNac, contactoObj, "CL-" + nroCliente);
            clienteService.guardarCliente(nuevoCliente); // Guarda en MySQL
            redirectAttributes.addFlashAttribute("mensaje", "Cliente guardado correctamente");

            return "redirect:/formularios/formularioRegistro";
        } else if (tipo.equals("empleado")) {
            Long idSucursalAux = Long.valueOf(idSucursal); // Casteo de idSucursal a Long
            Sucursal sucursal = sucursalService.obtenerPorId(idSucursalAux);
            // Verificamos si existe la sucursal
            if (sucursal == null) {
                redirectAttributes.addFlashAttribute("error", "El campo sucursal es obligatorio para un empleado");
                return "redirect:/formularios/formularioRegistro"; // Redirige al formulario con el error
            }

            // Creamos y guardamos empleado
            String nroLegajo = String.valueOf(totalEmpleados + 1);
            Empleado nuevoEmpleado = new Empleado(nombre, apellido, dni, fechaNac, contactoObj, "EM-" + nroLegajo);
            nuevoEmpleado.setSucursal(sucursal); // Asignamos la sucursal al empleado
            empleadoService.guardarEmpleado(nuevoEmpleado);
            redirectAttributes.addFlashAttribute("mensaje", "Empleado guardado correctamente");
            return "redirect:/formularios/formularioRegistro";
        }
        // Si no es ni cliente ni empleado, redirigimos con mensaje de error
        redirectAttributes.addFlashAttribute("error", "Tipo de persona no válido");
        return "redirect:/formularios/formularioRegistro";
    }
}