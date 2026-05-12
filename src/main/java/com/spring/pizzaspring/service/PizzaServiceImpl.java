package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.PizzaDTO;
import com.spring.pizzaspring.mapper.PizzaMapper;
import com.spring.pizzaspring.model.Pizza;
import com.spring.pizzaspring.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class PizzaServiceImpl implements PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private PizzaMapper pizzaMapper;

    @Override
    public PizzaDTO createPizza(PizzaDTO pizzaDTO) {
        Pizza pizza = pizzaMapper.DTOToPizza(pizzaDTO);
        Pizza savedPizza = pizzaRepository.save(pizza);
        return pizzaMapper.pizzaToDTO(savedPizza);
    }

    @Override
    public PizzaDTO updatePizza(Long id, PizzaDTO pizzaDTO) {
        Pizza existingPizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pizza not found."));

        existingPizza.setNome(pizzaDTO.getNome());
        existingPizza.setDescrizione(pizzaDTO.getDescrizione());
        existingPizza.setPrezzo(pizzaDTO.getPrezzo());

        Pizza updatedPizza = pizzaRepository.save(existingPizza);
        return pizzaMapper.pizzaToDTO(updatedPizza);
    }

    @Override
    public void deletePizza(Long id) {
        if (!pizzaRepository.existsById(id)) {
            throw new RuntimeException("Pizza not found.");
        }
        pizzaRepository.deleteById(id);
    }

    @Override
    public PizzaDTO getPizzaById(Long id) {
        Pizza pizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pizza not found."));
        return pizzaMapper.pizzaToDTO(pizza);
    }

    @Override
    public Collection<PizzaDTO> getAllPizze() {
        List<Pizza> pizze = pizzaRepository.findAll();
        return pizze.stream()
                .map(pizzaMapper::pizzaToDTO)
                .toList();
    }

    @Override
    public PizzaDTO patchPizzaPrice(Long id, Double newPrice) {
        Pizza pizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pizza not found with id: " + id));
        pizza.setPrezzo(newPrice);
        Pizza updatedPizza = pizzaRepository.save(pizza);
        return pizzaMapper.pizzaToDTO(updatedPizza);
    }
}
