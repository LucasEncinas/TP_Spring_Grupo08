package com.unla.grupo8.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.unla.grupo8.dtos.DiaCrearDTO;
import com.unla.grupo8.dtos.DiaDTO;
import com.unla.grupo8.entities.Dia;
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
    @Operation(summary = "Guarda un nuevo día", description = "Crea un día asociado a una sucursal si la fecha no está vencida.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Día guardado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos mal formateados o incompletos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    public ResponseEntity<?> guardarDia(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del día a crear", required = true, content = @Content(schema = @Schema(implementation = DiaCrearDTO.class))) @RequestBody DiaCrearDTO DiaCrearDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            diaService.guardarDiaDesdeDTO(DiaCrearDTO);
            response.put("success", true);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/por-sucursal-servicio")
    @Operation(summary = "Obtiene días por sucursal y servicio", description = "Devuelve una lista de días asociados a una sucursal y servicio específicos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Días obtenidos exitosamente", content = @Content(schema = @Schema(implementation = DiaDTO.class))),
    })
    public ResponseEntity<List<DiaDTO>> obtenerDiasPorSucursalYServicio(
            @Parameter(description = "ID de la sucursal", required = true) @RequestParam Long idSucursal,
            @Parameter(description = "ID del servicio", required = true) @RequestParam Long idServicio) {

        List<Dia> dias = diaService.obtenerDiasPorSucursalYServicio(idSucursal, idServicio);

        List<DiaDTO> dtos = dias.stream()
                .map(d -> new DiaDTO(d.getId(), d.getFecha(), d.getSucursal().getIdSucursal()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

}
