package com.spring.pizzaspring.repository;

import com.spring.pizzaspring.model.OrdinePizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdinePizzaRepository extends JpaRepository<OrdinePizza, Long> {
}
