package com.unla.grupo8.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.unla.grupo8.entities.Cliente;
import com.unla.grupo8.entities.Contacto;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByNroCliente(String nroCliente);

    boolean existsByContacto(Contacto contacto);

    Cliente findByContactoEmail(String email);

    @Query("SELECT MAX(c.nroCliente) FROM Cliente c")
    String findMaxNroCliente();

}
