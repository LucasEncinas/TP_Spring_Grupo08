package com.unla.grupo8.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unla.grupo8.entities.Servicio;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    List<Servicio> findByNombre(String nombre);

    List<Servicio> findByDuracion(Integer duracion);

    @Query("SELECT s FROM Servicio s LEFT JOIN FETCH s.sucursales")
    List<Servicio> findAllWithSucursales();

    @Query("SELECT s FROM Servicio s LEFT JOIN FETCH s.sucursales WHERE s.idServicio = :id")
    Servicio findByIdConSucursales(@Param("id") Long id);

}
