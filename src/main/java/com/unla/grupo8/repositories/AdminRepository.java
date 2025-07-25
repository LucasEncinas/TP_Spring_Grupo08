package com.unla.grupo8.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unla.grupo8.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByEmail(String email);
}
