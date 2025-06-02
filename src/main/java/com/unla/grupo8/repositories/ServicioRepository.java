package com.unla.grupo8.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unla.grupo8.entities.Servicio;

public interface  ServicioRepository extends JpaRepository<Servicio, Long> {

    List<Servicio> findByNombre(String nombre);

    List<Servicio> findByDuracion(Integer duracion);
    
}
