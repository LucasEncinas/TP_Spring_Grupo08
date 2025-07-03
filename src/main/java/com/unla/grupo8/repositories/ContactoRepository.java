package com.unla.grupo8.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unla.grupo8.entities.Contacto;

public interface ContactoRepository extends JpaRepository<Contacto, Long> {

    List<Contacto> findByDireccion(String direecion);

    List<Contacto> findByTelefono(String telefono);
    boolean existsByEmail(String email);

    Optional <Contacto> findByEmail(String email);
}