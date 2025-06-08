package com.unla.grupo8.service.implementation;

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

    public List<Sucursal> obtenerTodas() {
        return sucursalRepository.findAll();
    }

    public Sucursal obtenerPorId(Long id) {
        return sucursalRepository.findById(id).orElse(null);
    }

}
