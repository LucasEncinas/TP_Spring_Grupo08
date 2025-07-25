package com.unla.grupo8.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record DiaCrearDTO(
        @NotBlank LocalDate fecha,
        @NotBlank Long idSucursal) {
}
