package com.unla.grupo8.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.grupo8.dtos.ServicioDTO;
import com.unla.grupo8.entities.Servicio;
import com.unla.grupo8.entities.Sucursal;
import com.unla.grupo8.repositories.ServicioRepository;

@Service
public class ServicioService {

    @Autowired
    private final ServicioRepository servicioRepository;

    public List<ServicioDTO> obtenerTodosDTO() {
        List<Servicio> servicios = servicioRepository.findAllWithSucursales();

        return servicios.stream()
                .map(servicio -> new ServicioDTO(
                        servicio.getIdServicio(),
                        servicio.getNombre(),
                        servicio.getDuracion(),
                        servicio.getSucursales().stream()
                                .map(Sucursal::getIdSucursal)
                                .toList(),
                        servicio.getSucursales().stream()
                                .map(Sucursal::getNombre)
                                .toList()))
                .toList();
    }

    public ServicioService(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    public Servicio guardar(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    // obtener por id
    public Servicio obtenerPorId(Long id) {
        return servicioRepository.findByIdConSucursales(id);
    }

    public List<Servicio> obtenerServiciosPorNombre(String nombre) {
        return servicioRepository.findByNombre(nombre);
    }

    public List<Servicio> obtenerTodos() {
        return servicioRepository.findAllWithSucursales();
    }

    public void eliminarPorId(Long id) {
        servicioRepository.deleteById(id);
    }

    public boolean existePorNombre(String nombre) {
        return !servicioRepository.findByNombre(nombre).isEmpty();
    }

}
