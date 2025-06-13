package com.unla.grupo8.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unla.grupo8.entities.Cliente;
import com.unla.grupo8.entities.Contacto;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByNroCliente(String nroCliente);
    boolean existsByContacto(Contacto contacto);

}
