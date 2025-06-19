package com.unla.grupo8.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unla.grupo8.entities.Sucursal;

public interface SucursalRepository extends JpaRepository<Sucursal, Long> {

    Optional<Sucursal> findByNombre(String nombre);

    List<Sucursal> findByDireccion(String direccion);

    List<Sucursal> findByMail(String mail);

    List<Sucursal> findByTelefono(String telefono);

    boolean existsById(Long id);
    
}
