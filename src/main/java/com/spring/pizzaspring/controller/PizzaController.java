package com.spring.pizzaspring.controller;

import com.spring.pizzaspring.dto.PizzaDTO;
import com.spring.pizzaspring.service.PizzaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/pizza")
public class PizzaController {

    @Autowired
    private PizzaService service;

    @PostMapping(path = "/", consumes = "application/json")
    public ResponseEntity<PizzaDTO> carica(@RequestBody @Valid PizzaDTO pizzaDTO) {
        PizzaDTO dto = service.createPizza(pizzaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<PizzaDTO> aggiorna(@PathVariable Long id, @RequestBody PizzaDTO pizzaDTO) {
        PizzaDTO dto = service.updatePizza(id, pizzaDTO);
        return ResponseEntity.ok(dto);
    }

    public record PatchPrezzoDTO(@NotNull Double prezzo) {}

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<PizzaDTO> aggiornaPrezzo(@PathVariable Long id, @RequestBody @Valid PatchPrezzoDTO body) {
        PizzaDTO dto = service.patchPizzaPrice(id, body.prezzo());
        return ResponseEntity.ok(dto);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<PizzaDTO> cercaPerId(@PathVariable Long id) {
        PizzaDTO dto = service.getPizzaById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Collection<PizzaDTO>> getAll() {
        Collection<PizzaDTO> response = service.getAllPizze();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> eliminaPerId(@PathVariable Long id) {
        service.deletePizza(id);
        return ResponseEntity.noContent().build();
    }
}
