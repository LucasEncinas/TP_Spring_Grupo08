package com.unla.grupo8.controller;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

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
import com.unla.grupo8.service.implementation.DisponibilidadService;

@Controller
@RequestMapping("/disponibilidad") // Ruta base del controlador
public class DisponibilidadController {

    private final DisponibilidadService disponibilidadService;

    public DisponibilidadController(DisponibilidadService disponibilidadService) {
        this.disponibilidadService = disponibilidadService;
    }

     @GetMapping("/nuevaDisponibilidad")
    public String mostrarFormulario(Model model) { 
    List<Disponibilidad.Dia> diasList = Arrays.asList(Disponibilidad.Dia.values()); // Obtiene todos los valores del enum
    model.addAttribute("dias", diasList); // Enviamos la lista al modelo

    return "disponibilidad/nuevaDisponibilidad"; 
}

    // Guardar la nueva disponibilidad
     // Guardar la nueva disponibilidad
    @PostMapping("/guardar")
    public String guardarDisponibilidad(@RequestParam("dia") String dia, 
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

        Disponibilidad nuevaDisponibilidad = new Disponibilidad(horaInicio, horaFin, diaSeleccionado);
        disponibilidadService.guardarDisponibilidad(nuevaDisponibilidad);

        // Mensaje de éxito 
       attributes.addFlashAttribute("mensaje", "Disponibilidad guardada correctamente");
        return "redirect:/disponibilidad/nuevaDisponibilidad";
    }

    
}
