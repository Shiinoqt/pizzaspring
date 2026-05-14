package com.spring.pizzaspring.repository;

import com.spring.pizzaspring.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByTelefono(String telefono);
}
