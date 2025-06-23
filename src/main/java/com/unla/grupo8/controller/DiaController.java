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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unla.grupo8.entities.Dia;
import com.unla.grupo8.entities.Sucursal;
import com.unla.grupo8.service.implementation.DiaService;
import com.unla.grupo8.service.implementation.SucursalService;

@Controller
@RequestMapping("/dia")
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

    @PostMapping("/guardar")
    public ResponseEntity<Map<String, Object>> guardarDia(@RequestParam("fecha") String fecha,
            @RequestParam("idSucursal") Long idSucursal) {
        Map<String, Object> response = new HashMap<>();
        try {
            LocalDate localDate = LocalDate.parse(fecha);

            Sucursal sucursal = sucursalService.obtenerPorId(idSucursal);

            Dia dia = new Dia();
            dia.setFecha(localDate);
            dia.setSucursal(sucursal);

            diaService.guardarDia(dia);

            response.put("success", true);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Si algo sale mal, devolvemos error
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
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

    @GetMapping("/cargados/{idSucursal}")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> obtenerDiasCargados(@PathVariable("idSucursal") Long idSucursal) {
        List<Dia> dias = diaService.obtenerDiasPorSucursal(idSucursal);
        List<Map<String, Object>> eventos = new ArrayList<>();

        for (Dia dia : dias) {
            Map<String, Object> evento = new HashMap<>();
            evento.put("id", dia.getId()); // ID del dia
            evento.put("data.id", dia.getId()); // ID del evento
            evento.put("title", "Día cargado"); // titulo
            evento.put("start", dia.getFecha().toString()); // Formato YYYY-MM-DD
            evento.put("color", "#4CAF50"); // Verde
            eventos.add(evento);
        }

        return ResponseEntity.ok(eventos);
    }

}
