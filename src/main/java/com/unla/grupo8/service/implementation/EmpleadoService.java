package com.unla.grupo8.service.implementation;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.unla.grupo8.entities.Cliente;
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
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        empleado.setPassword(encoder.encode(empleado.getDni())); // Encriptar el DNI como contraseña
        empleado.setRol("EMPLEADO"); // Asigna el rol
        return empleadoRepository.save(empleado);
    }

    public long contarEmpleados() {
        return empleadoRepository.count(); // Cuenta el total de empleados en la BD
    }

    // Traer un empleado por su ID
    public Empleado traerEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    public List<Empleado> obtenerEmpleadosPorSucursal(Long idSucursal) {
        return empleadoRepository.findBySucursalIdSucursal(idSucursal);
    }

    // Traer todos los empleados
    public List<Empleado> obtenerTodos() {
        return empleadoRepository.findAll();
    }

    public Empleado modificarEmpleado(Empleado empleado) {
    return empleadoRepository.save(empleado);
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

    public Empleado findByContactoEmail(String email) {
        return empleadoRepository.findByContactoEmail(email);
    }

    public Empleado traerUltimoEmpleado() {
        List<Empleado> empleados = empleadoRepository.findAll();
        if (empleados.isEmpty()) {
            return null; // No hay empleados registrados
        }
        return empleados.get(empleados.size() - 1); // Retorna el último empleado de la lista
    }

}
