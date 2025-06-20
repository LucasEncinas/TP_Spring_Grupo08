package com.unla.grupo8.dtos;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiaDTO {
    private Long id;
    private LocalDate fecha;

    public DiaDTO(Long id, LocalDate fecha) {
        this.id = id;
        this.fecha = fecha;
    }
}
