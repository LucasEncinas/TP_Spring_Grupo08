package com.unla.grupo8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unla.grupo8.entities.Cliente;
import com.unla.grupo8.entities.Dia;
import com.unla.grupo8.entities.Empleado;
import com.unla.grupo8.entities.Servicio;
import com.unla.grupo8.entities.Turno;

import com.unla.grupo8.repositories.ServicioRepository;
import com.unla.grupo8.service.implementation.ClienteService;
import com.unla.grupo8.service.implementation.DiaService;
import com.unla.grupo8.service.implementation.EmpleadoService;
import com.unla.grupo8.service.implementation.ServicioService;
import com.unla.grupo8.service.implementation.TurnoService;

import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/turno")
public class TurnoController {

    private final TurnoService turnoService;
    private final ClienteService clienteService;
    private final EmpleadoService empleadoService;
    private final ServicioService servicioService;
    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private DiaService diaService;

    public TurnoController(TurnoService turnoService, ClienteService clienteService,
            EmpleadoService empleadoService,
            ServicioService servicioService) {
        this.turnoService = turnoService;
        this.clienteService = clienteService;
        this.empleadoService = empleadoService;
        this.servicioService = servicioService;
    }

    @GetMapping("/formularioTurno")
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
        return "turno/formularioTurno";
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
    public String guardarTurno(@ModelAttribute("turno") Turno turno) {
        // Asignar estado por defecto si está vacío
        if (turno.getEstado() == null || turno.getEstado().isEmpty()) {
            turno.setEstado("Confirmado");
        }
        // Validar que la fecha no sea nula
        if (turno.getDia() == null || turno.getDia().getFecha() == null) {
            throw new IllegalArgumentException("La fecha del turno no puede ser nula.");
        }
        // Obtener el servicio y asociar la sucursal si existe
        Servicio servicio = servicioRepository.findById(turno.getServicio().getIdServicio()).orElse(null);
        if (servicio != null && servicio.getSucursal() != null) {
            turno.setSucursal(servicio.getSucursal());
            turno.getDia().setSucursal(servicio.getSucursal());
        } else {
            System.out.println("⚠ El servicio no tiene una sucursal asociada.");
        }
        // Guardar el día primero
        Dia diaGuardado = diaService.guardarDiaR(turno.getDia());
        turno.setDia(diaGuardado);
        // Guardar el turno
        turnoService.guardar(turno);
        return "redirect:/empleado/index";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarTurno(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        System.out.println("ID a eliminar: " + id);
        turnoService.eliminarPorId(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "✅ Turno eliminado correctamente.");
        return "redirect:/empleado/index"; // o donde quieras redirigir
    }

    @GetMapping("/editar/{id}")
public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
    Turno turno = turnoService.buscarPorId(id);
    model.addAttribute("turno", turno);

    model.addAttribute("clientes", clienteService.traerTodosLosClientes());
    model.addAttribute("empleados", empleadoService.obtenerTodos());
    model.addAttribute("servicios", servicioService.obtenerTodos());

    return "turno/formularioTurno"; // el mismo HTML que ya usás
}


}
