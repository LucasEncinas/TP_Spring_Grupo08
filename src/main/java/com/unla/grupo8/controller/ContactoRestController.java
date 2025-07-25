package com.unla.grupo8.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unla.grupo8.dtos.ContactoDTO;
import com.unla.grupo8.entities.Cliente;
import com.unla.grupo8.entities.Contacto;
import com.unla.grupo8.entities.Empleado;
import com.unla.grupo8.entities.Persona;
import com.unla.grupo8.exception.ExcepcionContacto;
import com.unla.grupo8.service.implementation.ClienteService;
import com.unla.grupo8.service.implementation.ContactoService;
import com.unla.grupo8.service.implementation.EmpleadoService;
import com.unla.grupo8.service.implementation.PersonaService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/contactos")
public class ContactoRestController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private ContactoService contactoService;
    @Autowired
    private PersonaService personaService;

 // Ver contacto de un cliente
@GetMapping("/cliente/{id}")
public ResponseEntity<?> obtenerContactoCliente(@PathVariable Long id) {
    Cliente cliente = clienteService.traerClientePorId(id);
    if (cliente == null) {
        // El ID no corresponde a ningún cliente
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("El ID ingresado no corresponde a ningún cliente.");
    }
    Contacto contacto = cliente.getContacto();
    if (contacto == null) {
        // El cliente existe pero no tiene datos de contacto
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("El cliente no tiene datos de contacto registrados.");
    }
    ContactoDTO dto = new ContactoDTO(contacto.getEmail(), contacto.getTelefono(), contacto.getDireccion());
    return ResponseEntity.ok(dto);
}

// Ver contacto de un empleado
@GetMapping("/empleado/{id}")
public ResponseEntity<?> obtenerContactoEmpleado(@PathVariable Long id) {
    Empleado empleado = empleadoService.traerEmpleadoPorId(id);
    if (empleado == null) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("El ID ingresado no corresponde a ningún empleado.");
    }
    Contacto contacto = empleado.getContacto();
    if (contacto == null) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("El empleado no tiene datos de contacto registrados.");
    }
    ContactoDTO dto = new ContactoDTO(contacto.getEmail(), contacto.getTelefono(), contacto.getDireccion());
    return ResponseEntity.ok(dto);
}




    // Actualizo contacto de cliente
    @PutMapping("/cliente/{id}")
    public ResponseEntity<?> actualizarContactoCliente(@PathVariable Long id, @RequestBody ContactoDTO contactoDTO) {
        Cliente cliente = clienteService.traerClientePorId(id);
         if (cliente == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(Map.of("mensaje", "El ID proporcionado no corresponde a un cliente existente."));
    }
    if (cliente.getContacto() == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(Map.of("mensaje", "El cliente no tiene información de contacto registrada."));
    }
        Contacto contactoExistente = cliente.getContacto();
        contactoExistente.setTelefono(contactoDTO.getTelefono());
        contactoExistente.setEmail(contactoDTO.getEmail());
        contactoExistente.setDireccion(contactoDTO.getDireccion());
        try {
            Contacto contactoActualizado = contactoService.guardarContacto(contactoExistente);
            if (contactoActualizado == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                                     .body(Map.of("mensaje", "error"));
            }
            cliente.setContacto(contactoActualizado);
            clienteService.modificarCliente(cliente);

            return ResponseEntity.ok(contactoDTO);
        } catch (ExcepcionContacto e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body(Map.of("mensaje", "mail ya utilizado ingrese otro"));
        }
    }

    // Actualizo contacto de empleado
    @PutMapping("/empleado/{id}")
    public ResponseEntity<?> actualizarContactoEmpleado(@PathVariable Long id, @RequestBody ContactoDTO contactoDTO) {
        Empleado empleado = empleadoService.traerEmpleadoPorId(id);
        
        if (empleado == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(Map.of("mensaje", "El ID proporcionado no corresponde a un empleado existente."));
         }
        if (empleado.getContacto() == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(Map.of("mensaje", "El empleado no tiene información de contacto registrada."));
         }
        Contacto contactoExistente = empleado.getContacto();
        contactoExistente.setTelefono(contactoDTO.getTelefono());
        contactoExistente.setEmail(contactoDTO.getEmail());
        contactoExistente.setDireccion(contactoDTO.getDireccion());
        try {
            Contacto contactoActualizado = contactoService.guardarContacto(contactoExistente);
            if (contactoActualizado == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                                     .body(Map.of("mensaje", "error"));
            }
            empleado.setContacto(contactoActualizado);
            empleadoService.modificarEmpleado(empleado);
            return ResponseEntity.ok(contactoDTO);
        } catch (ExcepcionContacto e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body(Map.of("mensaje", "mail ya utilizado, ingrese otro"));
        }
    }

    
    //asociar contacto a una persona
    @PostMapping("/guardar")
    public ResponseEntity<?> guardarContacto(
            @RequestParam Long personaId,
            @RequestBody @Valid ContactoDTO contactoDTO) {

        try {
            Persona persona = personaService.obtenerPorId(personaId);
            if (persona == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("mensaje", "Persona no encontrada"));
            }

            Contacto contacto = new Contacto(contactoDTO.getEmail(), contactoDTO.getTelefono(), contactoDTO.getDireccion());
            contactoService.guardarContacto(contacto);

            if (persona instanceof Cliente cliente) {
                cliente.setContacto(contacto);
                clienteService.guardarCliente(cliente);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(Map.of("mensaje", "✔️ Contacto guardado y asociado al cliente"));
            } else if (persona instanceof Empleado empleado) {
                empleado.setContacto(contacto);
                empleadoService.guardarEmpleado(empleado);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(Map.of("mensaje", "✔️ Contacto guardado y asociado al empleado"));
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", "La persona no es Cliente ni Empleado"));

        } catch (ExcepcionContacto e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        }
    }

}
