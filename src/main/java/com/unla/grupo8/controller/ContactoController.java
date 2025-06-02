package com.unla.grupo8.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.grupo8.entities.Contacto;
import com.unla.grupo8.service.ContactoService;

@RestController
@RequestMapping("/contactos")

public class ContactoController {

    private final ContactoService contactoService;

    public ContactoController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    @GetMapping("/contacto/{direccion}")
    public List<Contacto> obtenerContactoPorDireccion(@PathVariable String direccion) {
        return contactoService.obtenerContactoPorDireccion(direccion);
    }

}
