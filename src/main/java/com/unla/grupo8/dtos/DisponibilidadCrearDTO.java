package com.unla.grupo8.dtos;

import java.util.List;

public record DisponibilidadCrearDTO(
        Long servicioId,
        List<String> dias,
        String horaDesde,
        String horaHasta) {
}