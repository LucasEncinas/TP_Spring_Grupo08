package com.unla.grupo8.controller;

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

    @GetMapping("/formularioServicio")
    public String mostrarFormularioNuevo(Model model) {
        // Obtenemos la lista de todas las sucursales
        List<Sucursal> sucursales = sucursalService.obtenerTodas();

        // Agregamos la lista de sucursales al modelo para que esté disponible en la vista
        model.addAttribute("sucursales", sucursales);
        return "servicios/formularioServicio";

    }

     @PostMapping("/guardar")
    public String guardarServicio(@RequestParam("nombre") String nombre,
                                    @RequestParam("duracion") String duracion,
                                     @RequestParam("sucursal") String idSucursal,
                                   RedirectAttributes redirectAttributes) {

        //Casteo de duracion Y idSucursal
        int duracionInt = Integer.parseInt(duracion);
        Long idSucursalAux = Long.valueOf(idSucursal); 
                       
        Sucursal sucursal = sucursalService.obtenerPorId(idSucursalAux);
        if (sucursal == null) {
            redirectAttributes.addFlashAttribute("error", "Sucursal no encontrada");
            return "redirect:/servicios/formularioServicio";
        }

        Servicio nuevoServicio = new Servicio(nombre, duracionInt);
        nuevoServicio.setSucursal(sucursal);
        servicioService.guardar(nuevoServicio);

        redirectAttributes.addFlashAttribute("mensaje", "Servicio guardado correctamente");
        return "redirect:/servicios/formularioServicio";
    }

    @GetMapping
    public String listarServicios(Model model) {
        List<Servicio> servicios = servicioService.obtenerTodos();
        model.addAttribute("servicios", servicios);

        return "servicios/listaServicios";
    }

    @GetMapping("/nombre/{nombre}")
    public List<Servicio> obtenerServiciosPorNombre(@PathVariable String nombre) {
        return servicioService.obtenerServiciosPorNombre(nombre);
    }

}
