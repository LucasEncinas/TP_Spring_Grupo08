package com.unla.grupo8.service.implementation;

import java.util.List;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.unla.grupo8.entities.Contacto;
import com.unla.grupo8.exception.ExcepcionContacto;
import com.unla.grupo8.repositories.ClienteRepository;
import com.unla.grupo8.repositories.ContactoRepository;
import com.unla.grupo8.repositories.EmpleadoRepository;

@Service
public class ContactoService {

    private final ContactoRepository contactoRepository;
    private final ClienteRepository clienteRepository;  
    private final EmpleadoRepository empleadoRepository;

    public ContactoService(ContactoRepository contactoRepository, ClienteRepository clienteRepository, EmpleadoRepository empleadoRepository) {
        this.contactoRepository = contactoRepository;
        this.clienteRepository = clienteRepository;
        this.empleadoRepository = empleadoRepository;
    }

    public void guardarContacto(Contacto contacto) {
    boolean existe = contactoRepository.existsByEmail(contacto.getEmail());

    if (existe) {
        Optional<Contacto> contactoExistente = contactoRepository.findByEmail(contacto.getEmail());

        if (contactoExistente.isPresent()) {
            Contacto contactoPersistido = contactoExistente.get();
            if (contacto.getId() != null && contacto.getId().equals(contactoPersistido.getId())) {
            } else {
                boolean enCliente = clienteRepository.existsByContacto(contactoPersistido);
                boolean enEmpleado = empleadoRepository.existsByContacto(contactoPersistido);

                if (enCliente || enEmpleado) {
                    throw new ExcepcionContacto("El email ya se encuentra registrado, ingrese otro.");
                }
            }
        }
    }

    contactoRepository.save(contacto);
}

    public List<Contacto> obtenerTodos() {
        return contactoRepository.findAll();
    }

    public Contacto obtenerPorId(Long id) {
        return contactoRepository.findById(id).orElse(null);
    }

}