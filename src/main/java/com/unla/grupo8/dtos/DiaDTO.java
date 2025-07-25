package com.unla.grupo8.dtos;

import java.time.LocalDate;

public record DiaDTO(
        Long id,
        LocalDate fecha,
        Long idSucursal) {
}
