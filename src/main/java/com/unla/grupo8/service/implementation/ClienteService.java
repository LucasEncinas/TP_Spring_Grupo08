package com.unla.grupo8.service.implementation;

import org.springframework.stereotype.Service;

import com.unla.grupo8.entities.Cliente;
import com.unla.grupo8.repositories.ClienteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

   public Cliente guardarCliente(Cliente cliente) {
    return clienteRepository.save(cliente);
   }



    public long contarClientes() {
        return clienteRepository.count(); // Cuenta el total de clientes en la BD
    }

    

    public Optional<Cliente> traerClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    // Traer todos los clientes
    public List<Cliente> traerTodosLosClientes() {
        return clienteRepository.findAll();
    }
    

}
