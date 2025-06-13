package com.unla.grupo8.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.unla.grupo8.exception.ExcepcionContacto;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExcepcionContacto.class)
    public String manejarContactoDuplicado(ExcepcionContacto ex, RedirectAttributes redirectAttributes) {
        return "contacto/index"; // 🔹 Redirige con el mensaje de error
    }
}

