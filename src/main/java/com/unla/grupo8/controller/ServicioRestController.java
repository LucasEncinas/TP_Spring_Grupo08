package com.unla.grupo8.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.grupo8.dtos.ServicioCrearDTO;
import com.unla.grupo8.dtos.ServicioDTO;
import com.unla.grupo8.entities.Servicio;
import com.unla.grupo8.entities.Sucursal;
import com.unla.grupo8.service.implementation.ServicioService;
import com.unla.grupo8.service.implementation.SucursalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/servicios")
@Tag(name = "Servicio", description = "Operaciones relacionadas a la gestión de servicios")
public class ServicioRestController {

        @Autowired
        private ServicioService servicioService;

        @Autowired
        private SucursalService sucursalService;

        public ServicioRestController(ServicioService servicioService) {
                this.servicioService = servicioService;
        }

        @GetMapping("/listar")
        public List<ServicioDTO> listarTodosLosServicios() {
                return servicioService.obtenerTodos().stream()
                                .map(servicio -> new ServicioDTO(
                                                servicio.getIdServicio(),
                                                servicio.getNombre(),
                                                servicio.getDuracion(),
                                                servicio.getSucursales().stream()
                                                                .map(Sucursal::getIdSucursal)
                                                                .toList(),
                                                servicio.getSucursales().stream()
                                                                .map(Sucursal::getNombre)
                                                                .toList()))
                                .toList();
        }

        @PostMapping("/guardar")
        @Operation(summary = "Crear o actualizar un servicio", description = "Crea un nuevo servicio o actualiza uno existente. La respuesta incluye el ID del servicio para usarlo en el endpoint de disponibilidades.")
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Servicio creado correctamente", content = @Content(schema = @Schema(example = "{\n  \"mensaje\": \"Servicio creado correctamente. Usá el ID 5 para llamar a /api/disponibilidades/guardar.\",\n  \"idServicio\": 5\n}"))),
                        @ApiResponse(responseCode = "200", description = "Servicio actualizado correctamente", content = @Content(schema = @Schema(example = "{\n  \"mensaje\": \"Servicio actualizado correctamente. ID del servicio: 5\",\n  \"idServicio\": 5\n}"))),
                        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(schema = @Schema(example = "{ \"mensaje\": \"El nombre es obligatorio\" }"))),
                        @ApiResponse(responseCode = "404", description = "Servicio no encontrado", content = @Content(schema = @Schema(example = "{ \"mensaje\": \"No se encontró el servicio para actualizar\" }"))),
                        @ApiResponse(responseCode = "409", description = "Conflicto por nombre duplicado", content = @Content(schema = @Schema(example = "{ \"mensaje\": \"Ya existe un servicio con ese nombre\" }")))
        })
        public ResponseEntity<Map<String, Object>> guardarServicio(@RequestBody ServicioCrearDTO dto) {
                try {
                        // Validar nombre
                        if (dto.nombre() == null || dto.nombre().trim().isEmpty()) {
                                return ResponseEntity.badRequest()
                                                .body(Map.of("mensaje", "El nombre es obligatorio"));
                        }

                        // Validar duración
                        if (dto.duracion() == null || dto.duracion() <= 0) {
                                return ResponseEntity.badRequest()
                                                .body(Map.of("mensaje", "La duración debe ser mayor a 0"));
                        }

                        // Validar sucursales
                        dto.idsSucursales().forEach(id -> System.out.println("Buscando sucursal id: " + id));

                        List<Sucursal> sucursales = dto.idsSucursales().stream()
                                        .map(sucursalService::obtenerPorId)
                                        .filter(Objects::nonNull)
                                        .collect(Collectors.toCollection(ArrayList::new));
                        System.out.println("Sucursales encontradas: " + sucursales.size());
                        if (sucursales.isEmpty()) {
                                return ResponseEntity.badRequest()
                                                .body(Map.of("mensaje", "No se encontraron sucursales válidas"));
                        }

                        // Validar nombre duplicado
                        List<Servicio> existentes = servicioService.obtenerServiciosPorNombre(dto.nombre());
                        boolean duplicado = existentes.stream()
                                        .anyMatch(s -> !Objects.equals(s.getIdServicio(), dto.id()));

                        if (!existentes.isEmpty() && duplicado) {
                                return ResponseEntity.status(HttpStatus.CONFLICT)
                                                .body(Map.of("mensaje", "Ya existe un servicio con ese nombre"));
                        }

                        // Crear o actualizar entidad Servicio
                        Servicio servicio;
                        if (dto.id() != null) {
                                servicio = servicioService.obtenerPorId(dto.id());
                                if (servicio == null) {
                                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                        .body(Map.of("mensaje",
                                                                        "No se encontró el servicio para actualizar"));
                                }
                        } else {
                                servicio = new Servicio();
                        }

                        servicio.setNombre(dto.nombre().trim());
                        servicio.setDuracion(dto.duracion());
                        servicio.setSucursales(sucursales);

                        servicioService.guardar(servicio);

                        Map<String, Object> response = new HashMap<>();
                        response.put("mensaje", (dto.id() == null)
                                        ? "Servicio creado correctamente. Usá el ID " + servicio.getIdServicio()
                                                        + " para llamar a /api/disponibilidades/guardar."
                                        : "Servicio actualizado correctamente. ID del servicio: "
                                                        + servicio.getIdServicio());
                        response.put("idServicio", servicio.getIdServicio());

                        return ResponseEntity.status(dto.id() == null ? HttpStatus.CREATED : HttpStatus.OK)
                                        .body(response);

                } catch (Exception e) {
                        e.printStackTrace();
                        return ResponseEntity.badRequest()
                                        .body(Map.of("mensaje", "Ocurrió un error al guardar el servicio"));
                }
        }

}