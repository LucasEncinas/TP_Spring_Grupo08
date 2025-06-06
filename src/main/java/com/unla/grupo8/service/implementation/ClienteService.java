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

    // Traer un cliente por su ID
    public Optional<Cliente> traerClientePorId(Long id) {
        return clienteRepository.findById(id);
    }


    // Traer todos los clientes
    public List<Cliente> traerTodosLosClientes() {
        return clienteRepository.findAll();
    }
 
}
