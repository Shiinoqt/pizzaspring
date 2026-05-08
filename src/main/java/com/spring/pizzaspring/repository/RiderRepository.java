package com.spring.pizzaspring.repository;

import com.spring.pizzaspring.model.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiderRepository extends JpaRepository<Rider, Long> {
}
