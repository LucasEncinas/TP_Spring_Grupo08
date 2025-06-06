package com.unla.grupo8.service.implementation;

import org.springframework.stereotype.Service;

import com.unla.grupo8.dtos.PersonaDTO;
import com.unla.grupo8.entities.Persona;

import com.unla.grupo8.repositories.PersonaRepository;
import com.unla.grupo8.service.IPersonService;

import java.util.List;
import java.util.Optional;


@Service
public class PersonaService implements IPersonService {

    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
	public List<Persona> getAll() {
		return personaRepository.findAll();
	}

    @Override
    public Optional<Persona> findById(long id) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }



    @Override
    public Persona findByName(String nombre) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByName'");
    }



    @Override
    public Persona insertOrUpdate(Persona persona) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertOrUpdate'");
    }



    @Override
    public boolean remove(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }



    @Override
    public List<PersonaDTO> findByDegreeName(String degreeNombre) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByDegreeName'");
    }

}
