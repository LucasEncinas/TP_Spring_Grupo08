package com.unla.grupo8.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.unla.grupo8.dtos.DiaDTO;
import com.unla.grupo8.service.implementation.DiaService;

@RestController
@RequestMapping("/api/dia")
@Tag(name = "Día", description = "Operaciones relacionadas a la gestión de días")
public class DiaRestController {

    @Autowired
    private DiaService diaService;

    public DiaRestController(DiaService diaService) {
        this.diaService = diaService;
    }

    @PostMapping("/guardar")
    @Operation(summary = "Guarda un nuevo día", description = "Crea un día si la fecha no está vencida.")
    public ResponseEntity<?> guardarDia(@RequestBody DiaDTO diaDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            diaService.guardarDiaDesdeDTO(diaDTO);
            response.put("success", true);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/por-sucursal/{idSucursal}")
    public List<DiaDTO> obtenerDias(@PathVariable Long idSucursal) {
        return diaService.obtenerDiasPorSucursal(idSucursal).stream()
                .map(d -> new DiaDTO(d.getId(), d.getFecha(), d.getSucursal().getIdSucursal()))
                .collect(Collectors.toList());
    }

}
