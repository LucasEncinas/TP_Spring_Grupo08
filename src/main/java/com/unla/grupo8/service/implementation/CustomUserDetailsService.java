package com.unla.grupo8.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.unla.grupo8.entities.Admin;
import com.unla.grupo8.entities.Cliente;
import com.unla.grupo8.entities.Empleado;
import com.unla.grupo8.repositories.AdminRepository;
import com.unla.grupo8.repositories.ClienteRepository;
import com.unla.grupo8.repositories.EmpleadoRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private AdminRepository adminRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByContactoEmail(email);
        if (cliente != null) {
            return User.builder()
                    .username(cliente.getContacto().getEmail())
                    .password(cliente.getPassword())
                    .roles(cliente.getRol())
                    .build();
        }
        Empleado empleado = empleadoRepository.findByContactoEmail(email);
        if (empleado != null) {
            return User.builder()
                    .username(empleado.getContacto().getEmail())
                    .password(empleado.getPassword())
                    .roles(empleado.getRol())
                    .build();
        }
        Admin admin = adminRepository.findByEmail(email);
        if (admin != null) {
            return User.builder()
                    .username(admin.getEmail())
                    .password(admin.getPassword())
                    .roles(admin.getRol())
                    .build();
        }
        throw new UsernameNotFoundException("Usuario no encontrado");
    }
}
