package com.unla.grupo8.service;

public interface IEmailService {
    void sendSimpleMessage(String to, String subject, String text);
}

