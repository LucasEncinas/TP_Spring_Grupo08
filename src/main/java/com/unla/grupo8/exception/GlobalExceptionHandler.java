package com.unla.grupo8.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExcepcionClienteEliminar.class)
    public String manejarEliminarCliente(ExcepcionContacto ex, RedirectAttributes redirectAttributes) {
        return "cliente/listaClientes";
    }

    @ExceptionHandler(ExcepcionContacto.class)
    public String manejarContactoDuplicado(ExcepcionContacto ex, RedirectAttributes redirectAttributes) {
        return "contacto/formularioContacto";
    }

    @ExceptionHandler(ExcepcionEmpleadoEliminar.class)
    public String manejarEliminarEmpleado(ExcepcionContacto ex, RedirectAttributes redirectAttributes) {
        return "empleado/listaEmpleados";
    }

    @ExceptionHandler(ExcepcionSucursalEliminar.class)
    public String manejarEliminarSucursal(ExcepcionContacto ex, RedirectAttributes redirectAttributes) {
        return "sucursal/formularioSucursal";
    }

    @ExceptionHandler(ExcepcionSucursalNombre.class)
    public String manejarNombreSucursalDuplicado(ExcepcionContacto ex, RedirectAttributes redirectAttributes) {
        return "sucursal/formularioSucursal";
    }

    @ExceptionHandler(ExcepcionTurno.class)
    public String manejarExcepcionTurno(ExcepcionTurno ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/turno/formularioTurno"; //
    }

    @ExceptionHandler(ExcepcionServicioNombre.class)
    public String manejarNombreRepetido(ExcepcionServicioNombre ex, RedirectAttributes redirect) {
        redirect.addFlashAttribute("mensajeError", ex.getMessage());
        return "redirect:/servicios/formularioServicio";
    }

}
