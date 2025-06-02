package com.unla.grupo8.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.grupo8.entities.Sucursal;
import com.unla.grupo8.service.SucursalService;

@RestController
@RequestMapping("/sucursales")
public class SucursalController {

    private final SucursalService sucursalService;

    public SucursalController(SucursalService sucursalService) {
        this.sucursalService = sucursalService;
    }
    
    @GetMapping("/sucursales")
    public List<Sucursal> obtenerSucursalPorNombre(@PathVariable String nombre) {
        return sucursalService.obtenerSucursalPorNombre(nombre);
    }
}
