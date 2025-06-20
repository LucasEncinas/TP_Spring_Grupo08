package com.unla.grupo8.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unla.grupo8.entities.Servicio;
import com.unla.grupo8.entities.Sucursal;
import com.unla.grupo8.exception.ExcepcionServicioNombre;
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
    public String listarServicios(Model model) {
        List<Servicio> servicios = servicioService.obtenerTodos();
        model.addAttribute("servicios", servicios);

        return "servicios/listaServicios";
    }

    @PostMapping("/guardar")
    public String guardarServicio(
            @ModelAttribute("servicio") Servicio servicio,
            @ModelAttribute("idSucursal") Long idSucursal,
            RedirectAttributes redirectAttributes) {

        Sucursal sucursal = sucursalService.obtenerPorId(idSucursal);
        if (sucursal == null) {
            redirectAttributes.addFlashAttribute("mensajeError", "Sucursal no encontrada");
            return servicio.getIdServicio() != null
                    ? "redirect:/servicios/editar/" + servicio.getIdServicio()
                    : "redirect:/servicios/formularioServicio";
        }

        boolean esNuevo = (servicio.getIdServicio() == null);

        // Validación para evitar nombres repetidos (cuando lo creamos o editamos)
        List<Servicio> existentesConMismoNombre = servicioService.obtenerServiciosPorNombre(servicio.getNombre());

        boolean nombreDuplicado = existentesConMismoNombre.stream()
                .anyMatch(s -> !s.getIdServicio().equals(servicio.getIdServicio()));

        if (!existentesConMismoNombre.isEmpty() && nombreDuplicado) {
            throw new ExcepcionServicioNombre("Ya existe un servicio con ese nombre");
        }

        servicio.setSucursales(new ArrayList<>());
        servicio.getSucursales().add(sucursal); // Asignar la sucursal al servicio
        servicioService.guardar(servicio);

        if (esNuevo) {
            redirectAttributes.addFlashAttribute("mensaje", "Servicio guardado correctamente, agregue disponibilidad");
        } else {
            redirectAttributes.addFlashAttribute("mensaje", "Servicio modificado correctamente, revise disponibilidad");
        }
        redirectAttributes.addFlashAttribute("servicioId", servicio.getIdServicio());
        return "redirect:/disponibilidad/nuevaDisponibilidad?servicioId=" + servicio.getIdServicio();
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarServicio(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Servicio servicio = servicioService.obtenerPorId(id);
            servicioService.eliminarPorId(id);
            redirectAttributes.addFlashAttribute("mensajeExito",
                    "✔️ Servicio '" + servicio.getNombre() + "' eliminada correctamente.");
        } catch (Exception e) {
            // TODO: handle exception
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
