package com.unla.grupo8.service.implementation;

import org.springframework.stereotype.Service;

import com.unla.grupo8.entities.Empleado;
import com.unla.grupo8.entities.Servicio;
import com.unla.grupo8.exception.ExcepcionEmpleadoEliminar;
import com.unla.grupo8.repositories.EmpleadoRepository;

import java.util.List;

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
        return empleadoRepository.count(); // Cuenta el total de empleados en la BD
    }

    // Traer un empleado por su ID
    public Empleado traerEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    // Traer todos los empleados
    public List<Empleado> obtenerTodos() {
        return empleadoRepository.findAll();
    }

    public void eliminarEmpleado(Empleado empleado) {
        Empleado empleadoAux = traerEmpleadoPorId(empleado.getIdPersona());
        if (!empleadoAux.getTurnos().isEmpty())
            throw new ExcepcionEmpleadoEliminar("❌ No se pueden eliminar empleados con turnos asignados.");
        // Eliminamos la relación con los servicios antes de borrar al empleado.
        for (Servicio servicio : empleadoAux.getServicios()) {
            servicio.getEmpleados().remove(empleadoAux);
        }
        empleadoAux.getServicios().clear();
        empleadoRepository.save(empleadoAux);
        empleadoRepository.delete(empleado);
    }

}
