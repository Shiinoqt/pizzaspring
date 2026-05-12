package com.spring.pizzaspring.controller;

import com.spring.pizzaspring.dto.PizzaDTO;
import com.spring.pizzaspring.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/pizza")
public class PizzaController {

    @Autowired
    private PizzaService service;

    @PostMapping(path = "/", consumes = "application/json")
    public PizzaDTO carica(@RequestBody PizzaDTO pizzaDTO) {
        return service.createPizza(pizzaDTO);
    }

    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public PizzaDTO aggiorna(@PathVariable Long id, @RequestBody PizzaDTO pizzaDTO) {
        return service.updatePizza(id, pizzaDTO);
    }

    @PatchMapping(path = "/{id}/price", produces = "application/json")
    public PizzaDTO aggiornaPrezzo(@PathVariable Long id, @RequestParam Double price) {
        return service.patchPizzaPrice(id, price);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public PizzaDTO cercaPerId(@PathVariable Long id) {
        return service.getPizzaById(id);
    }

    @GetMapping(path = "/", produces = "application/json")
    public Collection<PizzaDTO> getAll() {
        return service.getAllPizze();
    }

    @DeleteMapping(path = "/{id}")
    public void eliminaPerId(@PathVariable Long id) {
        service.deletePizza(id);
    }
}
