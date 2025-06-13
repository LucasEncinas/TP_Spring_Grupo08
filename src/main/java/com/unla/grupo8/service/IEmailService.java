package com.unla.grupo8.service;

import java.util.Map;

public interface IEmailService {
    void sendSimpleMessage(String to, String subject, String text);

    void sendHtmlMessage(String to, String subject, String templateName, Map<String, Object> templateModel);

}
