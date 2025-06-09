package com.unla.grupo8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unla.grupo8.entities.Contacto;
import com.unla.grupo8.service.implementation.ContactoService;

@Controller
@RequestMapping("/contacto")
public class ContactoController {

    private final ContactoService contactoService;

    @Autowired
    public ContactoController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    @GetMapping("/index")
    public String mostrarFormularioContacto() {
        return "contacto/index";
    }

    @PostMapping("/guardar")
    public String guardarContacto(
            @RequestParam("email") String email,
            @RequestParam("telefono") String telefono,
            @RequestParam("direccion") String direccion,
            @RequestParam(value = "redirect", required = false) String redirect, 
            RedirectAttributes redirectAttributes) {
        Contacto nuevoContacto = new Contacto(email, telefono, direccion);
        contactoService.guardarContacto(nuevoContacto);

        redirectAttributes.addFlashAttribute("mensaje", "Contacto guardado correctamente");

       if ("registro".equals(redirect)) {
        return "redirect:/formularios/formularioRegistro";
    }

        // Si no viene del formulario de registro, se mantiene en la página de contacto
        return "redirect:/contacto/index";
    }
}
