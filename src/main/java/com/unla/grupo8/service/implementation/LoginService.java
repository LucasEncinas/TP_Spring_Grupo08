package com.unla.grupo8.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.unla.grupo8.dtos.loginDTO;
import com.unla.grupo8.entities.Admin;
import com.unla.grupo8.entities.Cliente;
import com.unla.grupo8.entities.Empleado;
import com.unla.grupo8.repositories.AdminRepository;
import com.unla.grupo8.repositories.ClienteRepository;
import com.unla.grupo8.repositories.EmpleadoRepository;

@Service
public class LoginService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(loginDTO dto) {
        String email = dto.userName();
        String password = dto.password();

        Cliente cliente = clienteRepository.findByContactoEmail(email);
        if (cliente != null && passwordEncoder.matches(password, cliente.getPassword())) {
            return "cliente";
        }

        Empleado empleado = empleadoRepository.findByContactoEmail(email);
        if (empleado != null && passwordEncoder.matches(password, empleado.getPassword())) {
            return "empleado";
        }

        Admin admin = adminRepository.findByEmail(email);
        if (admin != null && passwordEncoder.matches(password, admin.getPassword())) {
            return "admin";
        }

        throw new BadCredentialsException("❌ Usuario o contraseña incorrectos.");
    }
}
