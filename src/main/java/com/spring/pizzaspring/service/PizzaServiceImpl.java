package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.PizzaDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class PizzaServiceImpl implements PizzaService {

    @Override
    public PizzaDTO createPizza(PizzaDTO pizzaDTO) {
        return null;
    }

    @Override
    public PizzaDTO updatePizza(Long id, PizzaDTO pizzaDTO) {
        return null;
    }

    @Override
    public void deletePizza(Long id) {

    }

    @Override
    public PizzaDTO getPizzaById(Long id) {
        return null;
    }

    @Override
    public Collection<PizzaDTO> getAllPizze() {
        return List.of();
    }
}
