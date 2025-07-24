package com.unla.grupo8.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unla.grupo8.entities.Cliente;
import com.unla.grupo8.entities.Turno;
import com.unla.grupo8.service.implementation.ClienteService;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/index")
    public String mostrarVistaCliente(Model model, Principal principal) {
        String email = principal.getName(); // Obtiene el correo del usuario logueado
        Cliente cliente = clienteService.findByContactoEmail(email);
        model.addAttribute("nombre", cliente.getNombre());
        model.addAttribute("turnos", cliente.getTurnos());
        return "cliente/index";
    }

    @GetMapping("/listaClientes")
    public String mostrarListaClientes(Model model) {
        List<Cliente> clientes = clienteService.traerTodosLosClientes();
        model.addAttribute("clientes", clientes);
        return "cliente/listaClientes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Cliente cliente = clienteService.traerClientePorId(id);
        clienteService.eliminarCliente(cliente);
        redirectAttributes.addFlashAttribute("mensajeExitoEliminar",
                "✔️ Cliente '" + cliente.getNombre() + " " + cliente.getApellido() + "' eliminado correctamente.");
        return "redirect:/cliente/listaClientes";
    }

    @GetMapping("/editar/{id}")
    public String modificarCliente(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.traerClientePorId(id);
        model.addAttribute("persona", cliente);
        model.addAttribute("tituloFormulario", "Modificar Cliente");
        return "formularios/formularioRegistro";
    }

    @GetMapping("/filtrar")
    public String filtrarTurnosCliente(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            Model model, Principal principal) {
        String email = principal.getName();
        Cliente cliente = clienteService.findByContactoEmail(email);
        List<Turno> turnosFiltrados = cliente.getTurnos().stream()
                .filter(t -> t.getDia().getFecha().equals(fecha))
                .toList();
        model.addAttribute("nombre", cliente.getNombre());
        model.addAttribute("turnos", turnosFiltrados);
        return "cliente/index";
    }

}
