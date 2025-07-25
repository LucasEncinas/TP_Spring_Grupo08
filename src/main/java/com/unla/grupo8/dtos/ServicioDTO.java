package com.unla.grupo8.dtos;

import java.util.List;

public record ServicioDTO(
    Long id,
    String nombre,
    int duracion,
    List<Long> idsSucursales,
    List<String> nombresSucursales
) {}



            
