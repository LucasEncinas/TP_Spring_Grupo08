package com.unla.grupo8.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.unla.grupo8.entities.Sucursal;
import com.unla.grupo8.repositories.SucursalRepository;

@Service
public class SucursalService {
    
    private final SucursalRepository sucursalRepository;

    public SucursalService(SucursalRepository sucursalRepository) {
        this.sucursalRepository = sucursalRepository;
    }

    public List<Sucursal> obtenerSucursalPorNombre(String nombre) {
        return sucursalRepository.findByNombre(nombre); 
    }
}
