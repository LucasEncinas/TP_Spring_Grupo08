package com.unla.grupo8.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unla.grupo8.entities.Servicio;
import com.unla.grupo8.entities.Sucursal;
import com.unla.grupo8.service.implementation.ServicioService;
import com.unla.grupo8.service.implementation.SucursalService;

@Controller
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private SucursalService sucursalService;

    @GetMapping({ "/formularioServicio", "/editar/{id}" })
    public String mostrarFormularioServicio(@PathVariable(required = false) Long id, Model model) {

        Servicio servicio;

        if (id != null) {
            servicio = servicioService.obtenerPorId(id);
            if (servicio == null) {
                return "redirect:/servicios/listaServicios";
            }
            model.addAttribute("tituloFormulario", "Editar Servicio");
        } else {
            servicio = new Servicio();
            model.addAttribute("tituloFormulario", "Nuevo Servicio");
        }

        List<Sucursal> sucursales = sucursalService.obtenerTodas();
        model.addAttribute("sucursales", sucursales);
        model.addAttribute("servicio", servicio);

        return "servicios/formularioServicio";
    }

   
    @GetMapping("/listaServicios")
    public String listarServicios() {
        return "servicios/listaServicios";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminarServicio(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Servicio servicio = servicioService.obtenerPorId(id);
            servicioService.eliminarPorId(id);
            redirectAttributes.addFlashAttribute("mensajeExito",
                    "✔️ Servicio '" + servicio.getNombre() + "' eliminada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError",
                    "❌ No se pueden eliminar servicios con turnos asignados.");
        }
        return "redirect:/servicios/listaServicios";
    }

    @GetMapping("/nombre/{nombre}")
    public List<Servicio> obtenerServiciosPorNombre(@PathVariable String nombre) {
        return servicioService.obtenerServiciosPorNombre(nombre);
    }

}
