package com.unla.grupo8.dtos;

import java.util.List;
 
 public record ServicioCrearDTO(
    Long id,
    String nombre,
    Integer duracion,
    List<Long> idsSucursales
) {}