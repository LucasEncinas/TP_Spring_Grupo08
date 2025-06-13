package com.unla.grupo8.service.implementation;

import java.util.List;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.unla.grupo8.entities.Contacto;
import com.unla.grupo8.exception.ExcepcionContacto;
import com.unla.grupo8.repositories.ClienteRepository;
import com.unla.grupo8.repositories.ContactoRepository;
import com.unla.grupo8.repositories.EmpleadoRepository;

@Service
public class ContactoService {

    private final ContactoRepository contactoRepository;
    private final ClienteRepository clienteRepository;  
    private final EmpleadoRepository empleadoRepository;

    public ContactoService(ContactoRepository contactoRepository, ClienteRepository clienteRepository, EmpleadoRepository empleadoRepository) {
        this.contactoRepository = contactoRepository;
        this.clienteRepository = clienteRepository;
        this.empleadoRepository = empleadoRepository;
    }

    public void guardarContacto(Contacto contacto) {
    boolean existe = contactoRepository.existsByEmailAndTelefonoAndDireccion(
        contacto.getEmail(), contacto.getTelefono(), contacto.getDireccion()
    );

    if (existe) {
        System.out.println(" Contacto ya existe en la BD, obteniendo el contacto guardado...");
        Optional<Contacto> contactoExistente = contactoRepository.findByEmailAndTelefonoAndDireccion(
            contacto.getEmail(), contacto.getTelefono(), contacto.getDireccion()
        );

        if (contactoExistente.isPresent()) {
            Contacto contactoPersistido = contactoExistente.get(); 

            boolean enCliente = clienteRepository.existsByContacto(contactoPersistido);
            boolean enEmpleado = empleadoRepository.existsByContacto(contactoPersistido);

            if (enCliente || enEmpleado) { 
                System.out.println(" Contacto ya registrado con otro usuario, NO se puede reutilizar.");
                throw new ExcepcionContacto("Este contacto ya está asignado a otra persona.");
            }
        }
    }

    System.out.println(" Guardando contacto en la BD...");
    contactoRepository.save(contacto);
}

    public List<Contacto> obtenerTodos() {
        return contactoRepository.findAll();
    }

    public Contacto obtenerPorId(Long id) {
        return contactoRepository.findById(id).orElse(null);
    }

}
