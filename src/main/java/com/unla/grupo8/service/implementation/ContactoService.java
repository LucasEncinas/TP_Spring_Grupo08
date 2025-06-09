package com.unla.grupo8.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;
import com.unla.grupo8.entities.Contacto;
import com.unla.grupo8.repositories.ContactoRepository;

@Service
public class ContactoService {

    private final ContactoRepository contactoRepository;

    public ContactoService(ContactoRepository contactoRepository) {
        this.contactoRepository = contactoRepository;
    }

    public void guardarContacto(Contacto contacto) {
        contactoRepository.save(contacto); // Guarda el contacto en la base de datos
    }

    public List<Contacto> obtenerTodos() {
        return contactoRepository.findAll();
    }

    public Contacto obtenerPorId(Long id) {
        return contactoRepository.findById(id).orElse(null);
    }

}
