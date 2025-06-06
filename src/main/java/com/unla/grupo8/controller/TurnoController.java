package com.unla.grupo8.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.unla.grupo8.entities.Cliente;
import com.unla.grupo8.entities.Empleado;
import com.unla.grupo8.entities.Servicio;
import com.unla.grupo8.entities.Turno;

import com.unla.grupo8.repositories.ServicioRepository;
import com.unla.grupo8.service.implementation.ClienteService;
import com.unla.grupo8.service.implementation.EmpleadoService;
import com.unla.grupo8.service.implementation.ServicioService;
import com.unla.grupo8.service.implementation.TurnoService;


import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/turnos")
public class TurnoController {

    private final TurnoService turnoService;
    private final ClienteService clienteService;
    private final EmpleadoService empleadoService;
    private final ServicioService servicioService;
    @Autowired
    private ServicioRepository servicioRepository;

    public TurnoController(TurnoService turnoService, ClienteService clienteService,
            EmpleadoService empleadoService,
            ServicioService servicioService) {
        this.turnoService = turnoService;
        this.clienteService = clienteService;
        this.empleadoService = empleadoService;
        this.servicioService = servicioService;
    }

    @GetMapping("/test")
    public String testView() {
        return "test";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioTurno(Model model) {
        model.addAttribute("turno", new Turno());
        List<Cliente> clientes = clienteService.traerTodosLosClientes();
        List<Empleado> empleados = empleadoService.obtenerTodos();
        List<Servicio> servicios = servicioService.obtenerTodos();

        if (clientes.isEmpty() || empleados.isEmpty() || servicios.isEmpty()) {
            model.addAttribute("error", "No hay datos suficientes para crear un turno.");
            return "error/noHayDatos"; // Podrías crear una vista de error
        }

        model.addAttribute("clientes", clientes);
        model.addAttribute("empleados", empleados);
        model.addAttribute("servicios", servicios);
        return "formularios/formularioTurno";
    }

    @GetMapping("/hora/{hora}")
    public List<Turno> obtenerTurnosPorHora(@PathVariable LocalTime hora) {
        return turnoService.obtenerTurnosPorHora(hora);
    }

    @GetMapping("/estado/{estado}")
    public List<Turno> obtenerTurnosPorEstado(@PathVariable String estado) {
        return turnoService.obtenerTurnosPorEstado(estado);
    }

    @PostMapping("/guardar")
    public String guardarTurno(@ModelAttribute Turno turno) {
        // Obtener el servicio desde el repositorio
        Servicio servicio = servicioRepository.findById(turno.getServicio().getIdServicio()).orElse(null);

        // Asignar la sucursal si el servicio tiene una asociada
        if (servicio != null && servicio.getSucursal() != null) {
            turno.setSucursal(servicio.getSucursal());
        } else {
            System.out.println("El servicio no tiene una sucursal asociada.");
        }

        // Guardar el turno con la sucursal asignada (si la hay)
        turnoService.guardar(turno);
        System.out.println("Guardando turno: " + turno);

        return "test";//"redirect:/turnos/listar"; // Redirigir después de guardar
    }

}
