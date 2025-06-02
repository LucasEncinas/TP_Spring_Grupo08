package com.unla.grupo8.service;

import org.springframework.stereotype.Service;

import com.unla.grupo8.entities.Persona;

import com.unla.grupo8.repositories.PersonaRepository;
import java.util.List;


@Service
public class PersonaService {

    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    

    // Traer todas las personas
    public List<Persona> traerTodasLasPersonas() {
        return personaRepository.findAll();
    }

}
