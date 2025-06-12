package com.unla.grupo8.controller;

import java.lang.annotation.Repeatable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unla.grupo8.entities.Cliente;
import com.unla.grupo8.entities.Contacto;
import com.unla.grupo8.entities.Empleado;
import com.unla.grupo8.entities.Persona;
import com.unla.grupo8.service.implementation.ClienteService;
import com.unla.grupo8.service.implementation.ContactoService;
import com.unla.grupo8.service.implementation.DisponibilidadService;
import com.unla.grupo8.service.implementation.EmpleadoService;
import com.unla.grupo8.service.implementation.PersonaService;
import com.unla.grupo8.service.implementation.ServicioService;

@Controller
@RequestMapping("/contacto")
public class ContactoController {
 
    private final ContactoService contactoService;

     @Autowired
    private PersonaService personaService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private EmpleadoService empleadoService;

    public ContactoController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

     @GetMapping("/index")
    
public String mostrarFormularioContacto(@RequestParam("personaId") Long personaId, Model model) {
    Persona persona = personaService.obtenerPorId(personaId);

    if (persona == null) {
        model.addAttribute("error", "No se encontró la persona con ID: " + personaId);
        return "redirect:/formularios/formularioRegistro";
    }

    model.addAttribute("persona", persona);
    model.addAttribute("personaId", personaId);
    return "contacto/index"; 
}



    @PostMapping("/guardar")
public String guardarContacto(@RequestParam("personaId") Long personaId,
                              @RequestParam("email") String email,
                              @RequestParam("telefono") String telefono,
                              @RequestParam("direccion") String direccion,
                              RedirectAttributes redirectAttributes) {
    Persona persona = personaService.obtenerPorId(personaId);
    Contacto contacto = new Contacto(email, telefono, direccion);
    contactoService.guardarContacto(contacto);

    if (persona instanceof Cliente cliente) {
        cliente.setContacto(contacto);
        clienteService.guardarCliente(cliente);
    } else if (persona instanceof Empleado empleado) {
        empleado.setContacto(contacto);
        empleadoService.guardarEmpleado(empleado);
    }

    redirectAttributes.addFlashAttribute("mensaje", "Contacto guardado correctamente.");
    return "redirect:/formularios/formularioRegistro";
}

}
