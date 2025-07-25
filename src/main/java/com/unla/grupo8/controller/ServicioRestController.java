package com.unla.grupo8.controller;

import java.util.List;
import java.util.Objects;

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

import org.springframework.web.bind.annotation.RequestBody; 

@RestController
@RequestMapping("/api/servicios")
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
    public ResponseEntity<String> guardarServicio(@RequestBody ServicioCrearDTO dto) {
    try {
        // Validar nombre
        if (dto.nombre() == null || dto.nombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre es obligatorio");
        }

        // Validar duración
        if (dto.duracion() == null || dto.duracion() <= 0) {
            return ResponseEntity.badRequest().body("La duración debe ser mayor a 0");
        }

        // Validar sucursales
        List<Sucursal> sucursales = dto.idsSucursales().stream()
            .map(sucursalService::obtenerPorId)
            .filter(Objects::nonNull)
            .toList();  

        if (sucursales.isEmpty()) {
            return ResponseEntity.badRequest().body("No se encontraron sucursales válidas");
        }

        // Validar nombre duplicado
        List<Servicio> existentes = servicioService.obtenerServiciosPorNombre(dto.nombre());
        boolean duplicado = existentes.stream()
            .anyMatch(s -> !Objects.equals(s.getIdServicio(), dto.id()));

        if (!existentes.isEmpty() && duplicado) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Ya existe un servicio con ese nombre");
        }

        // Crear o actualizar entidad Servicio
        Servicio servicio = new Servicio();

        if (dto.id() != null) {
            servicio.setIdServicio(dto.id());
        }

        servicio.setNombre(dto.nombre().trim());
        servicio.setDuracion(dto.duracion());
        servicio.setSucursales(sucursales);

        servicioService.guardar(servicio);

        String mensaje = (dto.id() == null)
            ? "Servicio creado correctamente"
            : "Servicio actualizado correctamente";

        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);

    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Ocurrió un error al guardar el servicio");
    }
}

}
