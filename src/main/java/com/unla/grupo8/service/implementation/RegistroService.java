package com.unla.grupo8.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.unla.grupo8.dtos.RegistroDTO;
import com.unla.grupo8.entities.Cliente;
import com.unla.grupo8.entities.Contacto;
import com.unla.grupo8.repositories.PersonaRepository;

@Service
public class RegistroService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Cliente guardarRegistro(RegistroDTO registroDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombre(registroDTO.getNombre());
        cliente.setApellido(registroDTO.getApellido());
        cliente.setDni(registroDTO.getDni());
        cliente.setFechaNacimiento(registroDTO.getFechaNacimiento());
        long totalClientes = clienteService.contarClientes();
        String nroCliente = String.valueOf(totalClientes + 1);
        cliente.setNroCliente(nroCliente);

        Contacto contacto = new Contacto();
        contacto.setDireccion(registroDTO.getDireccion());
        contacto.setTelefono(registroDTO.getTelefono());
        contacto.setEmail(registroDTO.getEmail());

        cliente.setContacto(contacto);

        cliente.setPassword(passwordEncoder.encode(registroDTO.getPassword()));
        cliente.setRol("CLIENTE");

        personaRepository.save(cliente);

        return cliente;
    }
}
