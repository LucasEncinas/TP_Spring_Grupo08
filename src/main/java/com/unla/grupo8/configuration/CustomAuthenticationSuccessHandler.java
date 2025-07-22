package com.unla.grupo8.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        String userType = authentication.getAuthorities().iterator().next().getAuthority();
    
        if ("ROLE_CLIENTE".equalsIgnoreCase(userType)) {
            response.sendRedirect("/cliente/index");
        } else if ("ROLE_EMPLEADO".equalsIgnoreCase(userType)) {
            response.sendRedirect("/inicio");
        } else if ("ROLE_ADMIN".equalsIgnoreCase(userType)) {
            response.sendRedirect("/inicio");
        } else {
            response.sendRedirect("/login");
        }

    }

}
