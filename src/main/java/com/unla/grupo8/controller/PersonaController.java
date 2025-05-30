package com.unla.grupo8.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.unla.grupo8.entities.Persona;
import com.unla.grupo8.service.PersonaService;
import java.util.List;
import java.util.Optional;

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

    // Traer persona por ID
    @GetMapping("/{id}")
    public ResponseEntity<Persona> traerPersonaPorId(@PathVariable Long id) {
        Optional<Persona> persona = personaService.traerPersonaPorId(id);
        return persona.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Modificar persona
    @PutMapping("/{id}")
    public ResponseEntity<Persona> modificarPersona(@PathVariable Long id, @RequestBody Persona personaActualizada) {
        personaActualizada.setId(id);
        return ResponseEntity.ok(personaService.modificarPersona(personaActualizada));
    }

    // Eliminar persona
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPersona(@PathVariable Long id) {
        personaService.eliminarPersona(id);
        return ResponseEntity.noContent().build();
    }

}
