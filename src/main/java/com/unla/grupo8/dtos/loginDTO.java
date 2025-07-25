package com.unla.grupo8.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record loginDTO(
        @Email @NotBlank String userName,
        @NotBlank String password) {
}
