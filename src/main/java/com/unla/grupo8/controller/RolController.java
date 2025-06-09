package com.unla.grupo8.controller;

import org.springframework.stereotype.Controller;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import ch.qos.logback.core.model.Model;

@Controller
@SessionAttributes("rolUsuario")

public class RolController {

    // Método para mostrar la vista de selección de rol
    @GetMapping("/rol")
    public String mostrarVistaRol() {
        return "seleccionRol/rol"; // Muestra la vista con los botones
    }

    @ModelAttribute("rolUsuario")
public String getRolUsuario() {
    return ""; // Inicializa la variable de sesión para evitar errores
}

 @GetMapping("/seleccionRol")
public String seleccionarRol(@RequestParam(value = "rol", required = false) String rol, HttpSession session) {
    System.out.println("Rol recibido: " + rol);

    if (rol != null) {
        session.setAttribute("rolUsuario", rol);
    }

    Object rolUsuarioObj = session.getAttribute("rolUsuario");
    String rolUsuario = rolUsuarioObj != null ? rolUsuarioObj.toString() : "";

    System.out.println("Rol en sesión: " + rolUsuario);

    if ("empleado".equals(rolUsuario)) {
        return "redirect:/empleado/index";
    } else if ("cliente".equals(rolUsuario)) {
        return "redirect:/cliente/index";
    }

    return "seleccionRol"; // Devuelve la vista si no hay redirección
}


}
