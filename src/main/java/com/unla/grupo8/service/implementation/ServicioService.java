package com.unla.grupo8.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;
import com.unla.grupo8.entities.Servicio;
import com.unla.grupo8.repositories.ServicioRepository;

@Service
public class ServicioService {

    private final ServicioRepository servicioRepository;

    public ServicioService(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    public void guardar(Servicio servicio) {
        servicioRepository.save(servicio);
    }

    // obtener por id
    public Servicio obtenerPorId(Long id) {
        return servicioRepository.findById(id).orElse(null);
    }

    public List<Servicio> obtenerServiciosPorNombre(String nombre) {
        return servicioRepository.findByNombre(nombre);
    }

    public List<Servicio> obtenerTodos() {
        return servicioRepository.findAll();
    }

    public void eliminarPorId(Long id) {
        servicioRepository.deleteById(id);
    }

    public boolean existePorNombre(String nombre) {
        return !servicioRepository.findByNombre(nombre).isEmpty();
    }

}
