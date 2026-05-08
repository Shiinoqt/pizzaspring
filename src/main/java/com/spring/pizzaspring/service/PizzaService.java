package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.PizzaDTO;

import java.util.Collection;

public interface PizzaService {
    PizzaDTO insertPizza(PizzaDTO pizzaDTO);
    PizzaDTO getPizzaById(Long id);
    Collection<PizzaDTO> selectAll();
    void deletePizza(Long id);
}
