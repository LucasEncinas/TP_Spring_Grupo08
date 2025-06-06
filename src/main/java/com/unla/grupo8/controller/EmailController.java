package com.unla.grupo8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.unla.grupo8.service.IEmailService;

@Controller
public class EmailController {

    private final IEmailService emailService;

    // Inyectamos el correo desde application.yml
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    public EmailController(IEmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/send-email")
    public String sendEmail(Model model) {
        emailService.sendSimpleMessage(fromEmail, "Prueba", "Esto es un correo de prueba.");
        model.addAttribute("emailDestino", fromEmail);
    return "email/enviado";
    }
}
