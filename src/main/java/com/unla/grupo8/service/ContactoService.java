package com.unla.grupo8.service;
import org.springframework.stereotype.Service;
import com.unla.grupo8.entities.Contacto;
import com.unla.grupo8.repositories.ContactoRepository;
import java.util.List;

@Service
public class ContactoService {

    private final ContactoRepository contactoRepository;

    public ContactoService(ContactoRepository contactoRepository) {
        this.contactoRepository = contactoRepository;
    }
    
    public List<Contacto> obtenerContactoPorDireccion(String direccion) {
        return contactoRepository.findByDireccion(direccion); 
    }
    
  
}
