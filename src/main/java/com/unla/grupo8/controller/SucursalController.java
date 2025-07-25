package com.unla.grupo8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unla.grupo8.entities.Sucursal;
import com.unla.grupo8.service.implementation.SucursalService;

@Controller
@RequestMapping("/sucursal")
public class SucursalController {

    private final SucursalService sucursalService;

    public SucursalController(SucursalService sucursalService) {
        this.sucursalService = sucursalService;
    }

    @GetMapping("/formularioSucursal")
    public String mostrarFormularioSucursal(Model model) {
    //     model.addAttribute("sucursal", new Sucursal());
        model.addAttribute("tituloFormulario", "Agregar Sucursal");
        return "sucursal/formularioSucursal";
    }

    @GetMapping("/listaSucursales")
    public String mostrarListaSucursales(/*Model model*/) {
        //List<Sucursal> sucursales = sucursalService.obtenerTodas();
        //model.addAttribute("sucursales", sucursales); SI ESTA COMENTADO SE PUEDE BORRAR
        return "sucursal/listaSucursales";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarSucursal(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Sucursal sucursal = sucursalService.obtenerPorId(id);
        sucursalService.eliminarPorId(id);
        redirectAttributes.addFlashAttribute("mensajeExitoEliminar",
                "✔️ Sucursal '" + sucursal.getNombre() + "' eliminada correctamente.");
        return "redirect:/sucursal/listaSucursales";
    }

    @GetMapping("/editar/{id}")
    public String modificarSucursal(/*@PathVariable Long id, Model model*/) {
        //Sucursal sucursal = sucursalService.obtenerPorId(id);
        //model.addAttribute("sucursal", sucursal);
        //model.addAttribute("tituloFormulario", "Modificar Sucursal");
        return "sucursal/formularioSucursal";
    }

    // @PostMapping("/guardar")
    // public String guardarSucursal(
    //         @RequestParam(value = "idSucursal", required = false) Long id,
    //         @RequestParam("nombre") String nombre,
    //         @RequestParam("direccion") String direccion,
    //         @RequestParam("telefono") String telefono,
    //         @RequestParam("mail") String mail,
    //         RedirectAttributes redirectAttributes) {
    //     Sucursal sucursal;
    //     if (id != null) {
    //         sucursal = sucursalService.obtenerPorId(id);
    //         sucursal.setNombre(nombre);
    //         sucursal.setDireccion(direccion);
    //         sucursal.setTelefono(telefono);
    //         sucursal.setMail(mail);
    //     } else {
    //         sucursal = new Sucursal(nombre, direccion, telefono, mail);
    //     }

    //     sucursalService.guardarSucursal(sucursal);

    //     if (id != null) {
    //         redirectAttributes.addFlashAttribute("mensajeExitoModificar",
    //                 "✔️ Sucursal '" + sucursal.getNombre() + "' modificada correctamente.");
    //     } else {
    //         redirectAttributes.addFlashAttribute("mensajeExitoCrear",
    //                 "✔️ Sucursal '" + sucursal.getNombre() + "' creada correctamente.");
    //     }

    //     return "redirect:/sucursal/listaSucursales";
    // }

}
