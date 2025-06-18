package com.unla.grupo8.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExcepcionContacto.class)
    public String manejarContactoDuplicado(ExcepcionContacto ex, RedirectAttributes redirectAttributes) {
        return "contacto/formularioContacto";
    }

    @ExceptionHandler(ExcepcionSucursalNombre.class)
    public String manejarNombreSucursalDuplicado(ExcepcionContacto ex, RedirectAttributes redirectAttributes) {
        return "sucursal/formularioSucursal";
    }
}
