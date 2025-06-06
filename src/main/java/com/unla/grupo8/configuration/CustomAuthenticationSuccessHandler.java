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
public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
    System.out.println("⚡ Autenticación exitosa! Ejecutando CustomAuthenticationSuccessHandler...");

    String userType = request.getParameter("userType");
    System.out.println("Valor de userType recibido: " + userType);
      if ("cliente".equalsIgnoreCase(userType)) { 
        System.out.println(" Redirigiendo a cliente/index");
        response.sendRedirect("cliente/index"); 
    } else if ("empleado".equalsIgnoreCase(userType)) {
        System.out.println(" Redirigiendo a /empleado/index");
        response.sendRedirect("/empleado/index"); 
    } else {
        System.out.println(" Error: Redirigiendo por defecto a /empleado/index");
        response.sendRedirect("/empleado/index"); 
    }


}

}

