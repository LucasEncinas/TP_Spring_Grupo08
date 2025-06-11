package com.unla.grupo8.controller;

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

        // Generación del cuerpo del correo
        String cuerpo = "Tu turno fue reservado con éxito.\n\n"
                + "Fecha: " + turno.getDia().getFecha() + "\n"
                + "Hora: " + turno.getHora() + "\n"
                + "Servicio: " + turno.getServicio().getNombre() + "\n"
                + "Empleado: " + turno.getEmpleado().getNombre() + "\n"
                + "Sucursal: " + turno.getSucursal().getNombre() + "\n\n"
                + "Gracias por elegirnos.";

        // Envío del correo
        emailService.sendSimpleMessage(fromEmail, "¡Turno reservado con éxito!", cuerpo);

        // Agregamos el correo al modelo para mostrarlo en la vista
        model.addAttribute("emailDestino", fromEmail);

        return "email/enviado"; // Devuelve la vista donde se muestra el mensaje de éxito
    }

}
