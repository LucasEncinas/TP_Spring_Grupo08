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
import com.unla.grupo8.entities.Turno;
import com.unla.grupo8.repositories.ClienteRepository;
import com.unla.grupo8.repositories.EmpleadoRepository;
import com.unla.grupo8.repositories.ServicioRepository;
import com.unla.grupo8.service.IEmailService;
import com.unla.grupo8.service.implementation.ClienteService;
import com.unla.grupo8.service.implementation.DiaService;
import com.unla.grupo8.service.implementation.EmpleadoService;
import com.unla.grupo8.service.implementation.ServicioService;
import com.unla.grupo8.service.implementation.TurnoService;

import java.time.LocalDate;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private DiaService diaService;

    private final IEmailService emailService;

    public TurnoController(TurnoService turnoService, ClienteService clienteService,
            EmpleadoService empleadoService,
            ServicioService servicioService,
            IEmailService emailService) {
        this.turnoService = turnoService;
        this.clienteService = clienteService;
        this.empleadoService = empleadoService;
        this.servicioService = servicioService;
        this.emailService = emailService;
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
    public String guardarTurno(@ModelAttribute("turno") Turno turno, Model model) {

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

        // Recuperamos versiones completas de servicio, empleado y cliente desde la base
        // de datos, ya que los objetos recibidos pueden estar incompletos (solo con el
        // ID cargado).
        Servicio servicioCompleto = servicioRepository.findById(turno.getServicio().getIdServicio())
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));
        turno.setServicio(servicioCompleto);

        Empleado empleadoCompleto = empleadoRepository.findById(turno.getEmpleado().getIdPersona())
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));
        turno.setEmpleado(empleadoCompleto);

        Cliente clienteCompleto = clienteRepository.findById(turno.getCliente().getIdPersona())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        turno.setCliente(clienteCompleto);

        // Accedemos al email del cliente desde el objeto Contacto, que ya fue cargado
        // al recuperar el cliente completo
        String emailCliente = clienteCompleto.getContacto().getEmail();

        Map<String, Object> variables = new HashMap<>();
        variables.put("nombreCliente", clienteCompleto.getNombre());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fechaFormateada = diaGuardado.getFecha().format(formatter);
        variables.put("fecha", fechaFormateada);
        variables.put("hora", turno.getHora());
        variables.put("servicio", turno.getServicio().getNombre());
        variables.put("empleado", turno.getEmpleado().getNombre());
        variables.put("sucursal", turno.getSucursal().getNombre());

        emailService.sendHtmlMessage(
                emailCliente,
                "¡Turno confirmado!",
                "email/turno-confirmado",
                variables);

        model.addAttribute("emailDestino", emailCliente);

        return "email/enviado";

    }

    @GetMapping("/eliminar/{id}")
    public String eliminarTurno(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        System.out.println("ID a eliminar: " + id);
        turnoService.eliminarPorId(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "✅ Turno eliminado correctamente.");
        return "redirect:/empleado/index";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Turno turno = turnoService.buscarPorId(id);
        model.addAttribute("turno", turno);

        model.addAttribute("clientes", clienteService.traerTodosLosClientes());
        model.addAttribute("empleados", empleadoService.obtenerTodos());
        model.addAttribute("servicios", servicioService.obtenerTodos());

        return "turno/formularioTurno";
    }

    @GetMapping("/filtrar")
    public String filtrarPorFecha(@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            Model model) {
        List<Turno> turnosFiltrados = turnoService.obtenerPorFecha(fecha);
        model.addAttribute("turnos", turnosFiltrados);
        return "empleado/index";
    }
}
