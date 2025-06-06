package com.unla.grupo8.dtos;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class PersonaDTO {
	private int id;

	private String nombre;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaDeNacimiento;

	public PersonaDTO(int id, String nombre, LocalDate fechaDeNacimiento) {
		this.setId(id);
		this.nombre = nombre;
		this.fechaDeNacimiento = fechaDeNacimiento;
	}
}