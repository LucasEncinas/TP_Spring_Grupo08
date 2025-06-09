package com.unla.grupo8.controller;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unla.grupo8.entities.Disponibilidad;
import com.unla.grupo8.entities.Disponibilidad.Dia;
import com.unla.grupo8.entities.Servicio;
import com.unla.grupo8.service.implementation.DisponibilidadService;
import com.unla.grupo8.service.implementation.ServicioService;

@Controller
@RequestMapping("/disponibilidad") // Ruta base del controlador
public class DisponibilidadController {

    private final DisponibilidadService disponibilidadService;
    @Autowired
   private ServicioService servicioService;

    public DisponibilidadController(DisponibilidadService disponibilidadService) {
        this.disponibilidadService = disponibilidadService;
    }

     @GetMapping("/nuevaDisponibilidad")
     public String mostrarFormularioDisponibilidad(@RequestParam("servicioId") Long servicioId, Model model) { 
    // Obtener el servicio asociado
    Servicio servicio = servicioService.obtenerPorId(servicioId);
    model.addAttribute("servicio", servicio); // Pasamos el servicio a la vista

    // Obtener todos los valores del enum Día
    List<Disponibilidad.Dia> diasList = Arrays.asList(Disponibilidad.Dia.values());
    model.addAttribute("dias", diasList); // Pasamos la lista de días al modelo

    return "disponibilidad/nuevaDisponibilidad"; 
    }


    
     // Guardar la nueva disponibilidad
    @PostMapping("/guardar")
    public String guardarDisponibilidad(@RequestParam("servicioId") Long servicioId,
                                        @RequestParam("dia") String dia, 
                                        @RequestParam ("horaDesde")String horaDesde,
                                        @RequestParam ("horaHasta") String horaHasta,
                                        RedirectAttributes attributes) {
        //casteo dia
        Dia diaSeleccionado;
        try {
            diaSeleccionado = Dia.valueOf(dia.toUpperCase());
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("error", "Día inválido.");
            return "redirect:/disponibilidad/nuevaDisponibilidad";
        }
        LocalTime horaInicio = LocalTime.parse(horaDesde);
        LocalTime horaFin = LocalTime.parse(horaHasta);
         
         // 🔹 **Obtener el servicio asociado**
    Servicio servicio = servicioService.obtenerPorId(servicioId);
    if (servicio == null) {
        attributes.addFlashAttribute("error", "El servicio no existe.");
        return "redirect:/disponibilidad/nuevaDisponibilidad";
    }


        // 🔹 **Crear Disponibilidad vinculada al servicio**
    Disponibilidad nuevaDisponibilidad = new Disponibilidad(horaInicio, horaFin, diaSeleccionado);
    servicio.getDisponibilidades().add(nuevaDisponibilidad); // Asociar disponibilidad al servicio
    servicioService.guardar(servicio);
        // Mensaje de éxito 
       attributes.addFlashAttribute("mensaje", "Servicio y disponibilidad guardada correctamente");
        return "redirect:/servicios/formularioServicio";
    }

    
}
