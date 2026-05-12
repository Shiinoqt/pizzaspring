package com.spring.pizzaspring.repository;

import com.spring.pizzaspring.model.Cliente;
import com.spring.pizzaspring.model.Pizza;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PizzaRepositoryTest {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Test
    public void shouldSaveAndFindPizza() {
        Pizza pizza = new Pizza();
        pizza.setNome("Margherita");
        pizza.setDescrizione("Pomodoro, Mozzarella, Basilico");
        pizza.setPrezzo(5.99);

        Pizza savedPizza = pizzaRepository.save(pizza);
        Optional<Pizza> found = pizzaRepository.findById(savedPizza.getIdPizza());

        assertTrue(found.isPresent());
        assertNotNull(savedPizza);
        assertEquals("Margherita",savedPizza.getNome());
    }

    @Test
    void shouldUpdatePizza() {
        Pizza pizza = new Pizza();
        pizza.setNome("Diavola");
        pizza.setDescrizione("Buonissimo");
        pizza.setPrezzo(4.99);
        pizzaRepository.save(pizza);

        pizza.setNome("Funghi");
        pizzaRepository.save(pizza);

        Pizza updated = pizzaRepository.findById(pizza.getIdPizza()).get();

        assertEquals("Funghi", updated.getNome());
    }

    @Test
    void shouldDeletePizza() {
        Pizza pizza = new Pizza();
        pizza.setNome("Diavola");
        pizza.setDescrizione("Buonissimo");
        pizza.setPrezzo(4.99);
        pizzaRepository.save(pizza);
        Long id = pizza.getIdPizza();

        pizzaRepository.deleteById(id);

        assertFalse(pizzaRepository.existsById(id));
    }
}