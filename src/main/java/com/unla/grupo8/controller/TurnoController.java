package com.unla.grupo8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unla.grupo8.entities.Cliente;
import com.unla.grupo8.entities.Dia;
import com.unla.grupo8.entities.Empleado;
import com.unla.grupo8.entities.Servicio;
import com.unla.grupo8.entities.Sucursal;
import com.unla.grupo8.entities.Turno;

import com.unla.grupo8.repositories.ServicioRepository;
import com.unla.grupo8.service.implementation.ClienteService;
import com.unla.grupo8.service.implementation.DiaService;
import com.unla.grupo8.service.implementation.EmpleadoService;
import com.unla.grupo8.service.implementation.ServicioService;
import com.unla.grupo8.service.implementation.TurnoService;

import java.time.LocalDate;
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
public String guardarTurno(@ModelAttribute Turno turno) {
    // Asegurar que el estado tenga un valor por defecto
    if (turno.getEstado() == null || turno.getEstado().isEmpty()) {
        turno.setEstado("Confirmado");
    }

    // Obtener el servicio y su sucursal asociada
    Servicio servicio = servicioRepository.findById(turno.getServicio().getIdServicio()).orElse(null);

    if (servicio != null && servicio.getSucursal() != null) {
        Sucursal sucursal = servicio.getSucursal();
        turno.setSucursal(sucursal);

        LocalDate fecha = turno.getDia().getFecha();

        // Buscar si ya existe un día con esa fecha y sucursal
        Dia diaExistente = diaService.buscarPorFechaYSucursal(fecha, sucursal);
        Dia diaFinal;

        if (diaExistente != null) {
            diaFinal = diaExistente;
        } else {
            Dia nuevoDia = new Dia();
            nuevoDia.setFecha(fecha);
            nuevoDia.setSucursal(sucursal);
            diaFinal = diaService.guardarDiaR(nuevoDia);
        }

        turno.setDia(diaFinal);
    } else {
        System.out.println("⚠ El servicio no tiene una sucursal asociada.");
    }

    // Guardar el turno en la base de datos
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

    @GetMapping("/filtrar")
    public String filtrarPorFecha(@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            Model model) {
        List<Turno> turnosFiltrados = turnoService.obtenerPorFecha(fecha);
        model.addAttribute("turnos", turnosFiltrados);
        return "empleado/index"; // o el nombre de tu vista
    }
}
