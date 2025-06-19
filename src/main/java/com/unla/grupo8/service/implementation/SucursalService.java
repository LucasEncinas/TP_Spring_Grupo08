package com.unla.grupo8.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unla.grupo8.entities.Sucursal;
import com.unla.grupo8.exception.ExcepcionSucursalEliminar;
import com.unla.grupo8.exception.ExcepcionSucursalNombre;
import com.unla.grupo8.repositories.SucursalRepository;

@Service
public class SucursalService {

    private final SucursalRepository sucursalRepository;

    public SucursalService(SucursalRepository sucursalRepository) {
        this.sucursalRepository = sucursalRepository;
    }

    public void guardarSucursal(Sucursal sucursal) {
        Optional<Sucursal> sucursalExistente = sucursalRepository.findByNombre(sucursal.getNombre());
        if (sucursalExistente.isPresent()) {
            boolean esMismaSucursal = sucursal.getIdSucursal() != null
                    && sucursal.getIdSucursal().equals(sucursalExistente.get().getIdSucursal());
            if (!esMismaSucursal) {
                throw new ExcepcionSucursalNombre(
                        "❌ Ya existe una sucursal con el nombre '" + sucursal.getNombre() + "'.");
            }
        }
        sucursalRepository.save(sucursal);
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
        Sucursal sucursal = obtenerPorId(id);
        if (!sucursal.getTurnos().isEmpty())
            throw new ExcepcionSucursalEliminar("❌ No se pueden eliminar sucursales con turnos asignados.");
        sucursalRepository.deleteById(id);
    }

}
