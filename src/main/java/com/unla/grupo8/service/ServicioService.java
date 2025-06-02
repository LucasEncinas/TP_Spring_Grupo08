package com.unla.grupo8.service;

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
    public List<Servicio> obtenerServiciosPorNombre(String nombre) {
        return servicioRepository.findByNombre(nombre);
    }
    
}
