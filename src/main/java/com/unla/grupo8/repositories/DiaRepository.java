package com.unla.grupo8.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unla.grupo8.entities.Dia;

public interface DiaRepository extends JpaRepository<Dia, Long> {

    List<Dia> findByFecha(LocalDate fecha);

    List<Dia> findBySucursalIdSucursal(Long idSucursal);

    Optional<Dia> findById(Long id);

    boolean existsByFechaAndSucursalIdSucursal(LocalDate fecha, Long idSucursal);

}
