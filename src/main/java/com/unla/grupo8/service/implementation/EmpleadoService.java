package com.unla.grupo8.service.implementation;

import org.springframework.stereotype.Service;

import com.unla.grupo8.entities.Empleado;
import com.unla.grupo8.repositories.EmpleadoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {
    
    private final EmpleadoRepository empleadoRepository;    

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public Empleado guardarEmpleado(Empleado empleado) {
       return empleadoRepository.save(empleado);
    }
    
    public long contarEmpleados() {
        return empleadoRepository.count(); // Cuenta el total de clientes en la BD
    }

      // Traer un empleado por su ID
    public Optional<Empleado> traerEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id);
    }

    // Traer todos los empleados
    public List<Empleado> traerTodosLosEmpleados() {
        return empleadoRepository.findAll();
    }

    public List<Empleado> obtenerTodos() {
        return empleadoRepository.findAll();
    }

}
