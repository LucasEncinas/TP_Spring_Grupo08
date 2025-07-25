package com.unla.grupo8.controller;

import com.unla.grupo8.dtos.loginDTO;
import com.unla.grupo8.service.implementation.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/login")
@Tag(name = "Login", description = "Endpoint para autenticación de usuarios")
public class LoginRestController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    @Operation(summary = "Login", description = "Verifica si las credenciales del usuario son válidas. Devuelve el rol si es exitoso.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso, devuelve el rol del usuario"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos mal formateados", content = @Content)
    })
    public ResponseEntity<?> login(
            @Valid @RequestBody(description = "Credenciales del usuario", required = true, content = @Content(schema = @Schema(implementation = loginDTO.class))) @org.springframework.web.bind.annotation.RequestBody loginDTO loginDTO) {
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