package com.unla.grupo8.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        String userType = request.getParameter("userType");
    
        if ("cliente".equalsIgnoreCase(userType)) {
            response.sendRedirect("cliente/index");
        } else if ("empleado".equalsIgnoreCase(userType)) {
            response.sendRedirect("empleado/index");
        } else {
            response.sendRedirect("empleado/index");
        }

    }

}
