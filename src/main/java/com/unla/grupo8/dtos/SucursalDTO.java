package com.unla.grupo8.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SucursalDTO(

    Long idSucursal,

    @NotBlank(message = "El nombre es obligatorio.")
    String nombre,

    @NotBlank(message = "La dirección es obligatoria.")
    String direccion,

    @NotBlank(message = "El teléfono es obligatorio.")
    String telefono,

    @NotBlank(message = "El mail es obligatorio.")
    @Email(message = "El mail debe tener un formato válido.")
    String mail

) {}

