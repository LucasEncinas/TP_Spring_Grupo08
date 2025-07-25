package com.unla.grupo8.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record DiaDTO(
                Long id,
                @NotBlank LocalDate fecha,
                @NotBlank Long idSucursal) {
}
