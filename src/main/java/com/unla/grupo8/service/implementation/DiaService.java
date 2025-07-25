package com.unla.grupo8.service.implementation;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.grupo8.dtos.DiaCrearDTO;
import com.unla.grupo8.entities.Dia;
import com.unla.grupo8.entities.Servicio;
import com.unla.grupo8.repositories.DiaRepository;
import com.unla.grupo8.repositories.ServicioRepository;
import com.unla.grupo8.repositories.SucursalRepository;

@Service
public class DiaService {

    private final DiaRepository diaRepository;

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    public DiaService(DiaRepository diaRepository) {
        this.diaRepository = diaRepository;
    }

    public void guardarDia(Dia dia) {
        if (dia.getFecha().isBefore(LocalDate.now())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaFormateada = dia.getFecha().format(formatter);
            throw new RuntimeException("❌ La fecha seleccionada (" + fechaFormateada
                    + ") ya ha vencido.\nPor favor, ingresa una fecha válida.");
        }
        diaRepository.save(dia);
    }

    public void guardarDiaDesdeDTO(DiaCrearDTO dto) {
        if (dto.fecha() == null) {
            throw new IllegalArgumentException("❌ La fecha es un campo obligatorio.");
        }
        boolean existe = diaRepository.existsByFechaAndSucursalIdSucursal(dto.fecha(), dto.idSucursal());
        if (existe) {
            throw new IllegalArgumentException("❌ Ya existe un día registrado para esa fecha en la sucursal.");
        }
        if (!sucursalRepository.existsById(dto.idSucursal())) {
            throw new IllegalArgumentException("❌ La sucursal con ID '" + dto.idSucursal() + "' no existe.");
        }
        Dia dia = new Dia();
        dia.setFecha(dto.fecha());
        dia.setSucursal(sucursalService.obtenerPorId(dto.idSucursal()));
        guardarDia(dia);
    }

    public List<Dia> obtenerDiasPorFecha(LocalDate fecha) {
        return diaRepository.findByFecha(fecha);
    }

    public List<Dia> obtenerDiasPorSucursal(Long idSucursal) {
        return diaRepository.findBySucursalIdSucursal(idSucursal);
    }

    public List<Dia> obtenerDiasPorSucursalYServicio(Long idSucursal, Long idServicio) {
        List<Dia> diasSucursal = diaRepository.findBySucursalIdSucursal(idSucursal);
        Optional<Servicio> servicioOpt = servicioRepository.findById(idServicio);

        if (!servicioOpt.isPresent()) {
            return Collections.emptyList();
        }

        Servicio servicio = servicioOpt.get();

        // Obtenemos el set de días permitidos según las disponibilidades del servicio
        Set<String> diasDisponibles = servicio.getDisponibilidades().stream()
                .map(d -> d.getDia().name())
                .collect(Collectors.toSet());

        return diasSucursal.stream()
                .filter(dia -> {
                    if (dia.getFecha() == null)
                        return false;

                    // Convertimos el DayOfWeek de la fecha a nombre en mayúsculas y español
                    String nombreDia = convertirADiaEspanol(dia.getFecha().getDayOfWeek());

                    return diasDisponibles.contains(nombreDia);
                })
                .distinct()
                .collect(Collectors.toList());
    }

    private String convertirADiaEspanol(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return "LUNES";
            case TUESDAY:
                return "MARTES";
            case WEDNESDAY:
                return "MIERCOLES";
            case THURSDAY:
                return "JUEVES";
            case FRIDAY:
                return "VIERNES";
            case SATURDAY:
                return "SABADO";
            case SUNDAY:
                return "DOMINGO";
            default:
                throw new IllegalArgumentException("Día inválido: " + dayOfWeek);
        }
    }

    public void eliminarDia(Long id) {
        diaRepository.deleteById(id);
    }

    public Dia obtenerPorId(Long eventoId) {
        return diaRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Día no encontrado con ID: " + eventoId));
    }

    public Dia buscarPorId(Long id) {
        return diaRepository.findById(id).orElse(null);
    }

}
