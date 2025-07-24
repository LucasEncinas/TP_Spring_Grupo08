package com.unla.grupo8.dtos;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegistroDTO {
    private String nombre;
    private String apellido;
    private String dni;
    private LocalDate fechaNacimiento;
    private String direccion;
    private String telefono;
    private String tipo;
    private String email;
    private String password;

    public RegistroDTO(String nombre, String apellido, String dni, LocalDate fechaNacimiento, String direccion,
            String telefono, String tipo, String email, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.tipo = tipo;
        this.email = email;
        this.password = password;
    }
}
