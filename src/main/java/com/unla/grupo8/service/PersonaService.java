package com.unla.grupo8.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.unla.grupo8.entities.Persona;

import com.unla.grupo8.repositories.PersonaRepository;
import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {

    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    // Traer una persona por su ID
    public Optional<Persona> traerPersonaPorId(Long id) {
        return personaRepository.findById(id);
    }

    // Traer todas las personas
    public List<Persona> traerTodasLasPersonas() {
        return personaRepository.findAll();
    }

    // Modificar una persona
    @Transactional
    public Persona modificarPersona(Persona personaActualizada) {
        return personaRepository.save(personaActualizada);
    }

    // Eliminar una persona
    @Transactional
    public void eliminarPersona(Long id) {
        personaRepository.deleteById(id);
    }

}
