package com.unla.grupo8.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.unla.grupo8.entities.Cliente;
import com.unla.grupo8.entities.Turno;
import com.unla.grupo8.service.implementation.ClienteService;

import ch.qos.logback.core.model.Model;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    
     @GetMapping("/index")
    public String mostrarVistaCliente() {
        return "cliente/index"; // ✅ Verifica que esté en `templates/cliente/index.html`
    }



    

  
    //traer todos los clientes
  //   @GetMapping
   // public ResponseEntity<List<Cliente>> traerTodosLosClientes() {
    //    return ResponseEntity.ok(clienteService.traerTodosLosClientes());
   // } 
  
    //traer cliente por ID
   // @GetMapping("/{id}")
   // public ResponseEntity<Cliente> traerClientePorId(@PathVariable Long id) {
    //    Optional<Cliente> cliente = clienteService.traerClientePorId(id);
    //    return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
   // }
    

  
}
