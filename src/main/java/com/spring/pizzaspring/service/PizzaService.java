package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.PizzaDTO;

import java.util.Collection;

public interface PizzaService {
    PizzaDTO createPizza(PizzaDTO pizzaDTO);
    PizzaDTO updatePizza(Long id, PizzaDTO pizzaDTO);
    void deletePizza(Long id);
    PizzaDTO getPizzaById(Long id);
    Collection<PizzaDTO> getAllPizze();
    PizzaDTO patchPizzaPrice(Long id, Double newPrice);
}
