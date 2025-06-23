package com.unla.grupo8.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExcepcionClienteEliminar.class)
    public String manejarEliminarCliente(ExcepcionClienteEliminar ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensajeErrorEliminar", ex.getMessage());
        return "redirect:/cliente/listaClientes";
    }

    @ExceptionHandler(ExcepcionContacto.class)
    public String manejarContactoDuplicado(ExcepcionContacto ex, RedirectAttributes redirectAttributes) {
        return "contacto/formularioContacto";
    }

    @ExceptionHandler(ExcepcionEmpleadoEliminar.class)
    public String manejarEliminarEmpleado(ExcepcionEmpleadoEliminar ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensajeErrorEliminar", ex.getMessage());
        return "redirect:/empleado/listaEmpleados";
    }

    @ExceptionHandler(ExcepcionSucursalEliminar.class)
    public String manejarEliminarSucursal(ExcepcionSucursalEliminar ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensajeErrorEliminar", ex.getMessage());
        return "redirect:/sucursal/listaSucursales";
    }

    @ExceptionHandler(ExcepcionSucursalNombre.class)
    public String manejarNombreSucursalDuplicado(ExcepcionSucursalNombre ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensajeErrorNombre", ex.getMessage());
        return "redirect:/sucursal/listaSucursales";
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
