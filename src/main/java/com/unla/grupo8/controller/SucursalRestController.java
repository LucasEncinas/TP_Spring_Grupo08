package com.unla.grupo8.controller;

import com.unla.grupo8.dtos.SucursalDTO;
import com.unla.grupo8.entities.Sucursal;
import com.unla.grupo8.service.implementation.SucursalService;

import jakarta.validation.Valid;

import com.unla.grupo8.exception.ExcepcionSucursalNombre;
import com.unla.grupo8.exception.ExcepcionSucursalEliminar;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/sucursales")
@Tag(name = "Sucursal", description = "Operaciones relacionadas a la gestión de sucursales")
public class SucursalRestController {

    private final SucursalService sucursalService;

    public SucursalRestController(SucursalService sucursalService) {
        this.sucursalService = sucursalService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las sucursales")
    public ResponseEntity<List<SucursalDTO>> obtenerTodas() {
        List<Sucursal> sucursales = sucursalService.obtenerTodas();

        List<SucursalDTO> dtos = sucursales.stream()
                .map(s -> new SucursalDTO(
                        s.getIdSucursal(),
                        s.getNombre(),
                        s.getDireccion(),
                        s.getTelefono(),
                        s.getMail()))
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una sucursal por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucursal encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SucursalDTO.class))),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada", content = @Content(schema = @Schema(example = "{ \"mensaje\": \"No se encontró la sucursal con ID 10\" }")))
    })
    public ResponseEntity<?> getById(
            @Parameter(description = "ID de la sucursal a buscar", required = true) @PathVariable Long id) {

        Sucursal sucursal = sucursalService.obtenerPorId(id);
        if (sucursal != null) {
            SucursalDTO dto = new SucursalDTO(
                    sucursal.getIdSucursal(),
                    sucursal.getNombre(),
                    sucursal.getDireccion(),
                    sucursal.getTelefono(),
                    sucursal.getMail());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró la sucursal con ID " + id));
        }
    }

    @PostMapping
    @Operation(summary = "Crear una nueva sucursal")
    public ResponseEntity<?> crearSucursal(
            @Parameter(description = "Datos de la sucursal a crear", required = true) @Valid @RequestBody SucursalDTO dto) {
        try {
            Sucursal sucursal = new Sucursal();
            sucursal.setIdSucursal(dto.idSucursal());
            sucursal.setNombre(dto.nombre());
            sucursal.setDireccion(dto.direccion());
            sucursal.setTelefono(dto.telefono());
            sucursal.setMail(dto.mail());

            sucursalService.guardarSucursal(sucursal);

            SucursalDTO response = new SucursalDTO(
                    sucursal.getIdSucursal(),
                    sucursal.getNombre(),
                    sucursal.getDireccion(),
                    sucursal.getTelefono(),
                    sucursal.getMail());

            return ResponseEntity.ok(response);

        } catch (ExcepcionSucursalNombre e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear la sucursal");
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una sucursal existente")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody SucursalDTO dto) {

        Sucursal existente = sucursalService.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensaje", "No se encontró la sucursal con ID " + id));
        }

        existente.setNombre(dto.nombre());
        existente.setDireccion(dto.direccion());
        existente.setTelefono(dto.telefono());
        existente.setMail(dto.mail());

        try {
            sucursalService.guardarSucursal(existente);

            SucursalDTO response = new SucursalDTO(
                    existente.getIdSucursal(),
                    existente.getNombre(),
                    existente.getDireccion(),
                    existente.getTelefono(),
                    existente.getMail());

            return ResponseEntity.ok(response);

        } catch (ExcepcionSucursalNombre e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una sucursal por ID")
    public ResponseEntity<?> borrar(
            @Parameter(description = "ID de la sucursal a eliminar", required = true) @PathVariable Long id) {
        try {
            sucursalService.eliminarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (ExcepcionSucursalEliminar e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
