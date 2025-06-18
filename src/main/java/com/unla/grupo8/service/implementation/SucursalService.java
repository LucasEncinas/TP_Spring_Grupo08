package com.unla.grupo8.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unla.grupo8.entities.Sucursal;
import com.unla.grupo8.exception.ExcepcionSucursalNombre;
import com.unla.grupo8.repositories.SucursalRepository;

@Service
public class SucursalService {

    private final SucursalRepository sucursalRepository;

    public SucursalService(SucursalRepository sucursalRepository) {
        this.sucursalRepository = sucursalRepository;
    }

    public void guardarSucursal(Sucursal sucursal) {
        if (sucursal.getIdSucursal() != null && sucursalRepository.existsById(sucursal.getIdSucursal())) {
            sucursalRepository.save(sucursal);
        } else {
            boolean existe = sucursalRepository.existsByNombre(sucursal.getNombre());

            if (existe) {
                Optional<Sucursal> sucursalExistente = sucursalRepository.findByNombre(sucursal.getNombre());

                if (sucursalExistente.isPresent())
                    throw new ExcepcionSucursalNombre("La sucursal '" + sucursal.getNombre() + "' ya existe.");
            }
            sucursalRepository.save(sucursal);
        }
    }

    public void guardar(Sucursal sucursal) {
        sucursalRepository.save(sucursal);
    }

    public Optional<Sucursal> obtenerSucursalPorNombre(String nombre) {
        return sucursalRepository.findByNombre(nombre);
    }

    public List<Sucursal> obtenerTodas() {
        return sucursalRepository.findAll();
    }

    public Sucursal obtenerPorId(Long id) {
        return sucursalRepository.findById(id).orElse(null);
    }

    public void eliminarPorId(Long id) {
        sucursalRepository.deleteById(id);
    }

}
