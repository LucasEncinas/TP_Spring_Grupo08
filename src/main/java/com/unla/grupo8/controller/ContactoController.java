package com.unla.grupo8.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
                              RedirectAttributes redirectAttributes) {
    Contacto nuevoContacto = new Contacto(email, telefono, direccion);
    contactoService.guardarContacto(nuevoContacto); // Guarda en MySQL

    redirectAttributes.addFlashAttribute("mensaje", "Contacto guardado correctamente");
    return "redirect:/contacto/index";
}
}
