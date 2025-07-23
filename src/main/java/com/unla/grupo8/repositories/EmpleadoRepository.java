package com.unla.grupo8.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.unla.grupo8.entities.Contacto;
import com.unla.grupo8.entities.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    List<Empleado> findByLegajo(String legajo);
    boolean existsByContacto(Contacto contacto);
    Empleado findByContactoEmail(String email);
}
