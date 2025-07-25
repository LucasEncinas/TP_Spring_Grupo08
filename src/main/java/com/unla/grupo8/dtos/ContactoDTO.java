package com.unla.grupo8.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ContactoDTO {
    private String email;
    private String telefono;
    private String direccion;

    // Constructor
    public ContactoDTO(String email, String telefono, String direccion) {
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
    }


}
