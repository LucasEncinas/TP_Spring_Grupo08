package com.unla.grupo8.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unla.grupo8.entities.Cliente;
import com.unla.grupo8.exception.ExcepcionClienteEliminar;
import com.unla.grupo8.service.implementation.ClienteService;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/index")
    public String mostrarVistaCliente() {
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
        try {
            Cliente cliente = clienteService.traerClientePorId(id);
            clienteService.eliminarCliente(cliente);
            redirectAttributes.addFlashAttribute("mensajeExitoEliminar",
                    "✔️ Cliente '" + cliente.getNombre() + "' eliminado correctamente.");
        } catch (ExcepcionClienteEliminar e) {
            // TODO: handle exception
            redirectAttributes.addFlashAttribute("mensajeErrorEliminar", e.getMessage());
        }
        return "redirect:/cliente/listaClientes";
    }

    // traer todos los clientes
    // @GetMapping
    // public ResponseEntity<List<Cliente>> traerTodosLosClientes() {
    // return ResponseEntity.ok(clienteService.traerTodosLosClientes());
    // }

    // traer cliente por ID
    // @GetMapping("/{id}")
    // public ResponseEntity<Cliente> traerClientePorId(@PathVariable Long id) {
    // Optional<Cliente> cliente = clienteService.traerClientePorId(id);
    // return cliente.map(ResponseEntity::ok).orElseGet(() ->
    // ResponseEntity.notFound().build());
    // }

}
