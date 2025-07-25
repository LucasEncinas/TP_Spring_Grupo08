package com.unla.grupo8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.unla.grupo8.dtos.loginDTO;
import com.unla.grupo8.service.implementation.LoginService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/login")
@Tag(name = "Login", description = "Endpoint para autenticación de usuarios")
public class LoginRestController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    @Operation(summary = "Login", description = "Verifica si las credenciales del usuario son válidas.")
    public ResponseEntity<?> login(@RequestBody loginDTO loginDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            String rol = loginService.login(loginDTO);
            response.put("success", true);
            response.put("rol", rol);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(401).body(response);
        }
    }
}
