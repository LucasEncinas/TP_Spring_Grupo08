package com.unla.grupo8.service;
import java.util.List;
import java.util.Optional;

import com.unla.grupo8.entities.Persona;
import com.unla.grupo8.dtos.PersonaDTO;

public interface IPersonService {

	public List<Persona> getAll();

	public Optional<Persona> findById(long id) throws Exception;

	public Persona findByName(String nombre) throws Exception;

	public Persona insertOrUpdate(Persona persona);

	public boolean remove(long id);

	public List<PersonaDTO> findByDegreeName(String degreeNombre);
}