package com.unla.grupo8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unla.grupo8.entities.Sucursal;
import com.unla.grupo8.service.implementation.SucursalService;

@Controller
@RequestMapping("/sucursal")
public class SucursalController {

    private final SucursalService sucursalService;

    public SucursalController(SucursalService sucursalService) {
        this.sucursalService = sucursalService;
    }
    
    @GetMapping("/formularioSucursal")
    public String mostrarFormularioNuevo() {
        return "sucursal/formularioSucursal";
    }

    @PostMapping("/guardar")
    public String guardarSucursal(@RequestParam("nombre") String nombre,
                                    @RequestParam("direccion") String direccion,
                                    @RequestParam("telefono") String telefono,
                                    @RequestParam("mail") String mail,
                                   RedirectAttributes redirectAttributes) {

        Sucursal nuevaSucursal = new Sucursal(nombre, direccion, telefono, mail);
        sucursalService.guardar(nuevaSucursal);

        redirectAttributes.addFlashAttribute("mensaje", "Sucursal guardado correctamente");
        return "redirect:/sucursal/formularioSucursal";
    }
}
