package com.unla.grupo8.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("error", "❌ Datos mal formateados o incompletos.");
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleMissingFields(HttpMessageNotReadableException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("error", "❌ Faltan campos requeridos en el cuerpo del JSON.");
        return ResponseEntity.badRequest().body(response);
    }

}
