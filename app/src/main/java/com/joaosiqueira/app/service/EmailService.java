package com.joaosiqueira.app.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void enviarEmail(String email) {
        System.out.println("Email enviado para " + email);
    }
}
