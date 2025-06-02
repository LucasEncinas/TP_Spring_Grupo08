package com.unla.grupo8.service;

import org.springframework.stereotype.Service;

import com.unla.grupo8.entities.Empleado;
import com.unla.grupo8.repositories.EmpleadoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {
    
    private final EmpleadoRepository empleadoRepository;    

    public EmpleadoService(EmpleadoRepository turnoRepository) {
        this.empleadoRepository = turnoRepository;
    }

     // Traer un empleado por su ID
    public Optional<Empleado> traerEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id);
    }


    // Traer todos los empleados
    public List<Empleado> traerTodosLosEmpleados() {
        return empleadoRepository.findAll();
    }

}
