package com.unla.grupo8.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unla.grupo8.entities.Dia;
import com.unla.grupo8.service.implementation.DiaService;
import com.unla.grupo8.service.implementation.SucursalService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RequestMapping("/dia")
@Tag(name = "Dia")
public class DiaController {

    private final DiaService diaService;
    private final SucursalService sucursalService;

    public DiaController(DiaService diaService, SucursalService sucursalService) {
        this.sucursalService = sucursalService;
        this.diaService = diaService;
    }

    @GetMapping("/calendario/{idSucursal}")
    public String mostrarCalendario(@PathVariable Long idSucursal, Model model) {
        model.addAttribute("idSucursal", idSucursal);
        model.addAttribute("nombreSucursal", sucursalService.obtenerPorId(idSucursal).getNombre());
        return "dia/calendario";
    }

    @GetMapping("/fecha/{fecha}")
    public List<Dia> obtenerDiasPorFecha(@PathVariable LocalDate fecha) {
        return diaService.obtenerDiasPorFecha(fecha);
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Map<String, Object>> eliminarDia(@RequestParam("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            diaService.eliminarDia(id);
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
