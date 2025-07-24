package com.unla.grupo8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unla.grupo8.entities.Cliente;
import com.unla.grupo8.entities.Contacto;
import com.unla.grupo8.entities.Empleado;
import com.unla.grupo8.entities.Persona;
import com.unla.grupo8.exception.ExcepcionContacto;
import com.unla.grupo8.service.implementation.ClienteService;
import com.unla.grupo8.service.implementation.ContactoService;
import com.unla.grupo8.service.implementation.EmpleadoService;
import com.unla.grupo8.service.implementation.PersonaService;

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

    @GetMapping("/formularioContacto")
    public String mostrarFormularioContacto(@RequestParam("personaId") Long personaId, Model model) {
        Persona persona = personaService.obtenerPorId(personaId);

        if (persona == null) {
            model.addAttribute("error", "No se encontró la persona con ID: " + personaId);
            return "redirect:/formularios/formularioRegistro";
        }

        model.addAttribute("persona", persona);
        model.addAttribute("personaId", personaId);
        model.addAttribute("contacto", new Contacto());
        return "contacto/formularioContacto";
    }

    @GetMapping("/verContacto/{id}") // ver contacto cliente
    public String verContacto(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.traerClientePorId(id);
        model.addAttribute("cliente", cliente);
        return "contacto/verContacto";
    }

    @GetMapping("/verContactoEmpleado/{id}") // ver contacto empleado
    public String verContactoEmpleado(@PathVariable Long id, Model model) {
        Empleado empleado = empleadoService.traerEmpleadoPorId(id);
        model.addAttribute("cliente", empleado);
        return "contacto/verContacto";
    }

    @GetMapping("/formularioContacto/{id}")
    public String editarContacto(@PathVariable Long id, Model model) {
        Contacto contacto = contactoService.obtenerPorId(id);

        model.addAttribute("contacto", contacto);

        Persona persona = personaService.obtenerPorContactoId(id);
        model.addAttribute("personaId", persona.getIdPersona());

        return "contacto/formularioContacto";
    }

    @PostMapping("/guardar")
    public String guardarContacto(
            @RequestParam("personaId") Long personaId,
            @RequestParam(value = "id", required = false) Long contactoId,
            @RequestParam("email") String email,
            @RequestParam("telefono") String telefono,
            @RequestParam("direccion") String direccion,
            Model model,
            RedirectAttributes redirectAttributes) {

        try {
            Persona persona = personaService.obtenerPorId(personaId);
            Contacto contacto;

            if (contactoId != null) {
                contacto = contactoService.obtenerPorId(contactoId);
                contacto.setEmail(email);
                contacto.setTelefono(telefono);
                contacto.setDireccion(direccion);
            } else {
                contacto = new Contacto(email, telefono, direccion);
            }

            contactoService.guardarContacto(contacto);

            if (persona instanceof Cliente cliente) {
                cliente.setContacto(contacto);
                clienteService.guardarCliente(cliente);
                redirectAttributes.addFlashAttribute("mensajeExitoCrear", "✔️ Cliente guardado correctamente.");
                return "redirect:/cliente/listaClientes";
            } else if (persona instanceof Empleado empleado) {
                empleado.setContacto(contacto);
                empleadoService.guardarEmpleado(empleado);
                redirectAttributes.addFlashAttribute("mensajeExitoCrear", "✔️ Empleado guardado correctamente.");
                return "redirect:/empleado/listaEmpleados";
            }

            return "redirect:/formularios/formularioRegistro";

        } catch (ExcepcionContacto e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("personaId", personaId);
            model.addAttribute("contacto", new Contacto(email, telefono, direccion));

            return "contacto/formularioContacto";
        }
    }

}
