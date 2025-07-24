package com.unla.grupo8.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.unla.grupo8.dtos.RegistroDTO;
import com.unla.grupo8.entities.Cliente;
import com.unla.grupo8.entities.Contacto;
import com.unla.grupo8.repositories.ClienteRepository;
import com.unla.grupo8.repositories.PersonaRepository;

@Service
public class RegistroService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Cliente guardarRegistro(RegistroDTO registroDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombre(registroDTO.getNombre());
        cliente.setApellido(registroDTO.getApellido());
        cliente.setDni(registroDTO.getDni());
        cliente.setFechaNacimiento(registroDTO.getFechaNacimiento());
        String maxNro = clienteRepository.findMaxNroCliente();
        long nextNro = (maxNro == null) ? 1 : Long.parseLong(maxNro) + 1;
        String nroCliente = String.valueOf(nextNro);
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
