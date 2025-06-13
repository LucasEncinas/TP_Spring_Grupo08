package com.unla.grupo8.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.unla.grupo8.entities.Turno;
import com.unla.grupo8.service.IEmailService;
import com.unla.grupo8.service.implementation.TurnoService;

@Controller
public class EmailController {

    private final IEmailService emailService;

    @Autowired
    private TurnoService turnoService;

    // Inyectamos el correo desde application.yml
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    public EmailController(IEmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/send-email")
    public String sendEmail(@RequestParam("idTurno") Long idTurno, Model model) {
        Turno turno = turnoService.buscarPorId(idTurno);

        String emailCliente = turno.getCliente().getContacto().getEmail();

        // Cuerpo del correo
        String cuerpo = "Tu turno fue reservado con éxito.\n\n"
                + "Fecha: " + turno.getDia().getFecha() + "\n"
                + "Hora: " + turno.getHora() + "\n"
                + "Servicio: " + turno.getServicio().getNombre() + "\n"
                + "Empleado: " + turno.getEmpleado().getNombre() + "\n"
                + "Sucursal: " + turno.getSucursal().getNombre() + "\n\n"
                + "Gracias por elegirnos.";

        // Enviamos del correo
        emailService.sendSimpleMessage(emailCliente, "¡Turno reservado con éxito!",
                cuerpo);

        // Agregamos el correo al modelo para mostrarlo en la vista
        model.addAttribute("emailDestino", emailCliente);

        return "email/enviado";
    }

    @GetMapping("/send-html-email")
    public String sendHtmlEmail(@RequestParam("idTurno") Long idTurno, Model model) {
        Turno turno = turnoService.buscarPorId(idTurno);

        String emailCliente = turno.getCliente().getContacto().getEmail();

        Map<String, Object> variables = new HashMap<>();
        variables.put("fecha", turno.getDia().getFecha());
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

}
