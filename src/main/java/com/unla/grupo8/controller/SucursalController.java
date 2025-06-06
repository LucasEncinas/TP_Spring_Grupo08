package com.unla.grupo8.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unla.grupo8.entities.Sucursal;
import com.unla.grupo8.service.implementation.SucursalService;

@Controller
@RequestMapping("/sucursales")
public class SucursalController {

    private final SucursalService sucursalService;

    public SucursalController(SucursalService sucursalService) {
        this.sucursalService = sucursalService;
    }
    
    @GetMapping("/sucursales/{nombre}")
    public List<Sucursal> obtenerSucursalPorNombre(@PathVariable String nombre) {
        return sucursalService.obtenerSucursalPorNombre(nombre);
    }
}
