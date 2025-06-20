package com.unla.grupo8.service.implementation;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unla.grupo8.entities.Disponibilidad;
import com.unla.grupo8.entities.Servicio;
import com.unla.grupo8.repositories.DisponibilidadRepository;

@Service
public class DisponibilidadService {

    private final DisponibilidadRepository disponibilidadRepository;
    private final ServicioService servicioService;
    @Autowired
    public DisponibilidadService(DisponibilidadRepository disponibilidadRepository, ServicioService servicioService,
            TurnoService turnoService) {
        this.disponibilidadRepository = disponibilidadRepository;
        this.servicioService = servicioService;
    }

    public void guardarDisponibilidad(Disponibilidad disponibilidad) {
        disponibilidadRepository.save(disponibilidad); // Guarda la disponibilidad en la BD
    }

    public List<Disponibilidad> obtenerDisponibilidadesPorDia(Disponibilidad.Dia dia) {
        return disponibilidadRepository.findByDia(dia);
    }

    public List<String> obtenerHorariosPorServicio(Long idServicio) {
        Servicio servicio = servicioService.obtenerPorId(idServicio);
        if (servicio == null)
            return List.of();

        int duracion = servicio.getDuracion(); // duración en minutos
        List<Disponibilidad> disponibilidades = disponibilidadRepository.findByServicio_IdServicio(idServicio);
        List<String> horarios = new ArrayList<>();

        for (Disponibilidad d : disponibilidades) {
            LocalTime inicio = d.getHoraDesde();
            LocalTime fin = d.getHoraHasta();

            // Solo agregamos horarios que permiten completar el turno dentro del rango
            while (!inicio.plusMinutes(duracion).isAfter(fin)) {
                horarios.add(inicio.toString());
                inicio = inicio.plusMinutes(duracion);
            }
        }

        return horarios;

    }

}
