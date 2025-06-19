package com.unla.grupo8.service;

import java.util.Map;

public interface IEmailService {
    void enviarMensajeSimple(String to, String subject, String text);

    void enviarMensajeHtml(String to, String subject, String templateName, Map<String, Object> templateModel);

}
