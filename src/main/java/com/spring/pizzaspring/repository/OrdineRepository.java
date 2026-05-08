package com.spring.pizzaspring.repository;

import com.spring.pizzaspring.model.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdineRepository extends JpaRepository<Ordine, Long> {
}