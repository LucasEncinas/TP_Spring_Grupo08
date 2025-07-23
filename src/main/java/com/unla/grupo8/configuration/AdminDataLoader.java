package com.unla.grupo8.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.unla.grupo8.entities.Admin;
import com.unla.grupo8.repositories.AdminRepository;

import jakarta.annotation.PostConstruct;

@Component
public class AdminDataLoader {
    
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (adminRepository.count() == 0) {
            Admin admin = new Admin();
            admin.setNombre("Administrador");
            admin.setEmail("admin@admin.com");
            admin.setPassword(passwordEncoder.encode("admin123")); // Contraseña encriptada
            admin.setRol("ADMIN");
            adminRepository.save(admin);
            System.out.println("Admin por defecto creado.");
        }
    }
}
