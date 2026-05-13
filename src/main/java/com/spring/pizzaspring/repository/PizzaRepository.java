package com.spring.pizzaspring.repository;

import com.spring.pizzaspring.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {
    boolean existsByNome(String nome);
}
