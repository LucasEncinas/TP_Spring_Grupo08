package com.unla.grupo8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unla.grupo8.entities.Cliente;

@Controller
@RequestMapping("/formularios")
public class RegistroController {

    @GetMapping("/formularioRegistro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "formularios/formularioRegistro"; // Nombre de la vista Thymeleaf
    }
} 