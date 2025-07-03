package com.unla.grupo8.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unla.grupo8.entities.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Long> {

    List<Persona> findByNombre(String nombre);

    List<Persona> findByApellido(String apellido);

    List<Persona> findByDni(String dni);

    List<Persona> findByFechaNacimiento(LocalDate fechaNacimiento);

    Optional <Persona> findByContactoId(Long contactoId);
}