package com.unla.grupo8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.unla.grupo8.dtos.RegistroDTO;
import com.unla.grupo8.entities.Cliente;
import com.unla.grupo8.entities.Empleado;
import com.unla.grupo8.entities.Sucursal;
import com.unla.grupo8.repositories.ClienteRepository;
import com.unla.grupo8.service.implementation.ClienteService;
import com.unla.grupo8.service.implementation.EmpleadoService;
import com.unla.grupo8.service.implementation.RegistroService;
import com.unla.grupo8.service.implementation.SucursalService;

@Controller
@RequestMapping("/formularios")
@SessionAttributes({ "clienteId", "empleadoId" })

public class RegistroController {

    @Autowired
    private RegistroService registroService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private SucursalService sucursalService;

    @GetMapping("/formularioRegistro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("sucursales", sucursalService.obtenerTodas());
        model.addAttribute("tituloFormulario", "Registrar Usuario");
        return "formularios/formularioRegistro";
    }

    @GetMapping("/formularioInicial")
    public String mostrarFormularioInicial(Model model) {
        model.addAttribute("tituloFormulario", "Registro");
        return "registro/formularioInicial";
    }

    @PostMapping("/guardarRegistro")
    public String guardarRegistro(@ModelAttribute("registroDTO") RegistroDTO registroDTO,
            RedirectAttributes redirectAttributes) {
        registroService.guardarRegistro(registroDTO);
        redirectAttributes.addFlashAttribute("mensajeExitoRegistro",
                "✔️ ¡Registro exitoso!");
        return "redirect:/login";
    }

    @PostMapping("/guardar")
    public String guardarPersona(
            @RequestParam(value = "idPersona", required = false) Long id,
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("dni") String dni,
            @RequestParam("fechaNacimiento") String fechaNacimiento,
            @RequestParam(value = "tipo", required = false) String tipo, // "cliente" o "empleado"
            @RequestParam(value = "sucursal", required = false) String idSucursal, // solo si es empleado

            RedirectAttributes redirectAttributes) {
        // Casteo de fecha de nacimiento
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter);

        if (tipo.equals("cliente")) {
            if (id != null) {
                Cliente cliente = clienteService.traerClientePorId(id);
                cliente.setNombre(nombre);
                cliente.setApellido(apellido);
                cliente.setDni(dni);
                cliente.setFechaNacimiento(fechaNac);
                clienteService.guardarCliente(cliente);
                redirectAttributes.addFlashAttribute("mensajeExitoModificar",
                        "✔️ Cliente 'nro " + cliente.getNroCliente() + "' modificado correctamente.");
                return "redirect:/cliente/listaClientes";
            } else {
                String maxNro = clienteRepository.findMaxNroCliente();
                long nextNro = (maxNro == null) ? 1 : Long.parseLong(maxNro) + 1;
                String nroCliente = String.valueOf(nextNro);
                Cliente cliente = new Cliente(nombre, apellido, dni, fechaNac, null, nroCliente);

                Cliente nuevoCliente = clienteService.guardarCliente(cliente);

                redirectAttributes.addFlashAttribute("mensaje",
                        "✔️ Cliente guardado correctamente, agregue un contacto.");
                return "redirect:/contacto/formularioContacto?personaId=" + nuevoCliente.getIdPersona();
            }
        } else {
            if (tipo.equals("empleado")) {
                Long idSucursalAux = Long.valueOf(idSucursal); // Casteo de idSucursal a Long
                Sucursal sucursal = sucursalService.obtenerPorId(idSucursalAux);
                if (id != null) {
                    Empleado empleado = empleadoService.traerEmpleadoPorId(id);
                    empleado.setNombre(nombre);
                    empleado.setApellido(apellido);
                    empleado.setDni(dni);
                    empleado.setFechaNacimiento(fechaNac);
                    empleado.setSucursal(sucursal);
                    empleadoService.guardarEmpleado(empleado);
                    redirectAttributes.addFlashAttribute("mensajeExitoModificar",
                            "✔️ Empleado '" + empleado.getLegajo() + "' modificado correctamente.");
                    return "redirect:/empleado/listaEmpleados";
                } else {
                    // Verificamos si existe la sucursal
                    if (sucursal == null) {
                        redirectAttributes.addFlashAttribute("error",
                                "El campo sucursal es obligatorio para un empleado");
                        return "redirect:/formularios/formularioRegistro"; // Redirige al formulario con el error
                    }
                    Empleado ultimoEmpleado = empleadoService.traerUltimoEmpleado();
                    String legajoCompleto = ultimoEmpleado.getLegajo();
                    String legajoNumero = legajoCompleto.substring(3);
                    long legajoNumeroLong = (legajoNumero == null) ? 1 : Long.parseLong(legajoNumero) + 1;
                    String nroLegajo = String.valueOf(legajoNumeroLong);

                    Empleado nuevoEmpleado = new Empleado(nombre, apellido, dni, fechaNac, null, "EM-" + nroLegajo);
                    nuevoEmpleado.setSucursal(sucursal); // Asignamos la sucursal al empleado
                    empleadoService.guardarEmpleado(nuevoEmpleado);

                    redirectAttributes.addFlashAttribute("mensaje",
                            "Empleado guardado correctamente, agregue un contacto");
                    return "redirect:/contacto/formularioContacto?personaId=" + nuevoEmpleado.getIdPersona();
                }
            }
        }
        // Si no es ni cliente ni empleado, redirigimos con mensaje de error
        redirectAttributes.addFlashAttribute("error", "Tipo de persona no válido");
        return "redirect:/formularios/formularioRegistro";
    }
}