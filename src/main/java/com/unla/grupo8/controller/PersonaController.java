package com.unla.grupo8.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.unla.grupo8.entities.Persona;
import com.unla.grupo8.service.PersonaService;
import java.util.List;


@RestController
@RequestMapping("/personas")
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    // Traer todas las personas
    @GetMapping
    public ResponseEntity<List<Persona>> traerTodasLasPersonas() {
        return ResponseEntity.ok(personaService.traerTodasLasPersonas());
    }

    

}
