package com.unla.grupo8.service.implementation;

import org.springframework.stereotype.Service;

import com.unla.grupo8.entities.Cliente;
import com.unla.grupo8.exception.ExcepcionClienteEliminar;
import com.unla.grupo8.repositories.ClienteRepository;

import java.util.List;

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

    public Cliente traerClientePorId(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    // Traer todos los clientes
    public List<Cliente> traerTodosLosClientes() {
        return clienteRepository.findAll();
    }

    public void eliminarCliente(Cliente cliente) {
        Cliente clienteAux = traerClientePorId(cliente.getIdPersona());
        if (!clienteAux.getTurnos().isEmpty())
            throw new ExcepcionClienteEliminar("❌ No se pueden eliminar clientes con turnos asignados.");
        clienteRepository.delete(cliente);
    }

}
