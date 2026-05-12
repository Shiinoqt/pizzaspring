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
public class OrdineRepositoryTest {

    @Autowired
    private OrdineRepository ordineRepository;

    @Autowired
    private OrdinePizzaRepository ordinePizzaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Test
    void shouldSaveAndFind() {
        // Saving cliente
        Cliente cliente = new Cliente();
        cliente.setNome("Mario Rossi");
        cliente.setIndirizzo("Via Roma 1");
        cliente.setTelefono("12345");
        clienteRepository.save(cliente);

        // Saving ordine
        Ordine ordine = new Ordine();
        ordine.setCodice("ORD-001");
        ordine.setCliente(cliente);
        ordineRepository.save(ordine);

        // Checks
        Optional<Ordine> found = ordineRepository.findById("ORD-001");
        assertTrue(found.isPresent());
        assertEquals(cliente.getNome(), found.get().getCliente().getNome());
    }

    @Test
    void shouldSaveAndFindOP() {
        Cliente cliente = new Cliente();
        cliente.setNome("Giuseppe Rossi");
        cliente.setIndirizzo("Via Roma 10");
        cliente.setTelefono("555-1234");
        clienteRepository.save(cliente);

        OrdinePrioritario ordinePrio = new OrdinePrioritario();
        ordinePrio.setCodice("PRIO-001");
        ordinePrio.setCliente(cliente);
        ordinePrio.setSovrapprezzo(2.50);

        ordineRepository.save(ordinePrio);

        Optional<Ordine> found = ordineRepository.findById("PRIO-001");

        assertTrue(found.isPresent());
        assertTrue(found.get() instanceof OrdinePrioritario);

        OrdinePrioritario op = (OrdinePrioritario) found.get();
        assertEquals(2.50, op.getSovrapprezzo());
    }

    @Test
    void shouldUpdateOrdineWithRider() {
        Cliente cliente = new Cliente();
        cliente.setNome("Mario Rossi");
        cliente.setIndirizzo("Via Roma 1");
        cliente.setTelefono("12345");
        clienteRepository.save(cliente);

        Rider rider = new Rider();
        rider.setNome("Flash");
        riderRepository.save(rider);

        Ordine ordine = new Ordine();
        ordine.setCodice("ORD-002");
        ordine.setCliente(cliente);
        ordineRepository.save(ordine);

        ordine.setRider(rider);
        ordineRepository.save(ordine);

        Ordine updated = ordineRepository.findById("ORD-002").get();

        assertNotNull(updated.getRider());
        assertEquals("Flash", updated.getRider().getNome());
    }

    @Test
    void shouldDeleteOrdine() {
        Cliente cliente = new Cliente();
        cliente.setNome("Mario Rossi");
        cliente.setIndirizzo("Via Roma 1");
        cliente.setTelefono("12345");
        clienteRepository.save(cliente);

        Ordine ordine = new Ordine();
        ordine.setCodice("ORD-003");
        ordine.setCliente(cliente);
        ordineRepository.save(ordine);

        ordineRepository.deleteById("ORD-003");

        assertFalse(ordineRepository.existsById("ORD-003"));
    }

    @Test
    void shouldDeleteOrdineAndOrdinePizze() {
        Cliente cliente = new Cliente();
        cliente.setNome("Test");
        cliente.setIndirizzo("Via X");
        cliente.setTelefono("123");
        clienteRepository.save(cliente);

        Pizza pizza = new Pizza();
        pizza.setNome("Margherita");
        pizza.setDescrizione("Desc");
        pizza.setPrezzo(5.0);
        pizzaRepository.save(pizza);

        Ordine ordine = new Ordine();
        ordine.setCodice("ORD-DELETE-TEST");
        ordine.setCliente(cliente);

        OrdinePizza op = new OrdinePizza();
        op.setPizza(pizza);
        op.setQuantita(1);
        op.setOrdine(ordine);
        ordine.setPizzeOrdinate(new java.util.ArrayList<>());
        ordine.getPizzeOrdinate().add(op);

        Ordine savedOrdine = ordineRepository.save(ordine);
        ordineRepository.flush();

        Long ordinePizzaId = savedOrdine.getPizzeOrdinate().iterator().next().getId();

        assertNotNull(ordinePizzaId, "The ID should not be null after saving");

        ordineRepository.delete(savedOrdine);
        ordineRepository.flush();

        assertFalse(ordineRepository.existsById("ORD-DELETE-TEST"));
        assertFalse(ordinePizzaRepository.existsById(ordinePizzaId));
        assertTrue(pizzaRepository.existsById(pizza.getIdPizza()));
    }
}