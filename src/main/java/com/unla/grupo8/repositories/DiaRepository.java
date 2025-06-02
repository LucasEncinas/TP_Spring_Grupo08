package com.unla.grupo8.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unla.grupo8.entities.Dia;

public interface DiaRepository extends JpaRepository<Dia, Long> {

    List<Dia> findByFecha(LocalDate fecha);

    // Puedes agregar más métodos personalizados si es necesario
    
}
