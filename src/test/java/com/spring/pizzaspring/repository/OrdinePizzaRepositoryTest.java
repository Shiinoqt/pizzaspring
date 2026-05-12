package com.spring.pizzaspring.repository;

import com.spring.pizzaspring.model.*;
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
public class OrdinePizzaRepositoryTest {

    @Autowired
    private OrdinePizzaRepository ordinePizzaRepository;

    @Autowired
    private OrdineRepository ordineRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void shouldSaveAndFindOrdinePizza() {
        Cliente cliente = new Cliente();
        cliente.setNome("Test Cliente");
        cliente.setIndirizzo("Via Test 1");
        cliente.setTelefono("123");
        clienteRepository.save(cliente);

        Ordine ordine = new Ordine();
        ordine.setCodice("ORD-999");
        ordine.setCliente(cliente);
        ordineRepository.save(ordine);

        Pizza pizza = new Pizza();
        pizza.setNome("Margherita");
        pizza.setDescrizione("Buonissimo");
        pizza.setPrezzo(5.0);
        pizzaRepository.save(pizza);

        OrdinePizza link = new OrdinePizza();
        link.setOrdine(ordine);
        link.setPizza(pizza);
        link.setQuantita(2);

        OrdinePizza saved = ordinePizzaRepository.save(link);

        Optional<OrdinePizza> found = ordinePizzaRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals(2, found.get().getQuantita());
        assertEquals("Margherita", found.get().getPizza().getNome());
        assertEquals("ORD-999", found.get().getOrdine().getCodice());
    }

    @Test
    void shouldDeleteOrdinePizza() {
        Cliente c = new Cliente();
        c.setNome("C");
        c.setIndirizzo("Addr");
        c.setTelefono("123");
        c = clienteRepository.save(c);

        Pizza p = new Pizza();
        p.setNome("P");
        p.setDescrizione("Desc");
        p.setPrezzo(10.0);
        p = pizzaRepository.save(p);

        Ordine o = new Ordine();
        o.setCodice("DEL-1");
        o.setCliente(c);
        o = ordineRepository.save(o);

        OrdinePizza op = new OrdinePizza();
        op.setOrdine(o);
        op.setPizza(p);
        op.setQuantita(1);
        op = ordinePizzaRepository.save(op);

        ordinePizzaRepository.deleteById(op.getId());

        assertFalse(ordinePizzaRepository.existsById(op.getId()));
        assertTrue(ordineRepository.existsById("DEL-1"));
    }
}