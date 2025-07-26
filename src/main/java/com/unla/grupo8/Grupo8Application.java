package com.unla.grupo8;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
    info = @Info(
        title = "API Grupo 8",
        version = "1.0.0",
        description = "API para gestión de sucursales, servicios, disponibilidad, dias, contacto - Grupo 8 UNLa",
        license = @License(
            name = "MIT License",
            url = "https://opensource.org/licenses/MIT"
        )
    )
)
@SpringBootApplication
public class Grupo8Application {

	public static void main(String[] args) {
		SpringApplication.run(Grupo8Application.class, args);
	}

}
