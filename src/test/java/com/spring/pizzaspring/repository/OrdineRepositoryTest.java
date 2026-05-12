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
    void shouldDeleteEverything() {
        // Save Pizza independently
        Pizza pizza = new Pizza();
        pizza.setNome("Margherita");
        pizza.setDescrizione("Test Desc");
        pizza.setPrezzo(5.0);
        Pizza managedPizza = pizzaRepository.save(pizza);

        // Save Cliente (no ordini yet)
        Cliente cliente = new Cliente();
        cliente.setNome("Test");
        cliente.setIndirizzo("Via X");
        cliente.setTelefono("123");
        cliente.setOrdini(new java.util.ArrayList<>());
        Cliente savedCliente = clienteRepository.saveAndFlush(cliente);

        // Build and save Ordine linked to Cliente
        Ordine ordine = new Ordine();
        ordine.setCodice("ORD-DELETE-TEST");
        ordine.setCliente(savedCliente);
        ordine.setPizzeOrdinate(new java.util.ArrayList<>());
        savedCliente.getOrdini().add(ordine);
        clienteRepository.saveAndFlush(savedCliente); // cascades to Ordine

        // Retrieve managed Ordine and attach OrdinePizza
        Ordine managedOrdine = ordineRepository.findById("ORD-DELETE-TEST").orElseThrow();
        OrdinePizza op = new OrdinePizza();
        op.setPizza(managedPizza);
        op.setQuantita(1);
        op.setOrdine(managedOrdine);              // owning side
        managedOrdine.getPizzeOrdinate().add(op); // inverse side
        ordineRepository.saveAndFlush(managedOrdine);

        // Verification of IDs
        assertTrue(ordineRepository.existsById("ORD-DELETE-TEST"));
        Long opId = managedOrdine.getPizzeOrdinate().get(0).getId();
        assertNotNull(opId);

        //Delete the Cliente directly
        // This will trigger CascadeType.REMOVE (or ALL) on ordini
        clienteRepository.delete(savedCliente);
        clienteRepository.flush();

        // Assertions
        assertFalse(clienteRepository.existsById(savedCliente.getIdCliente()), "Cliente should be gone");
        assertFalse(ordineRepository.existsById("ORD-DELETE-TEST"), "Ordine should be gone");
        assertFalse(ordinePizzaRepository.existsById(opId), "OrdinePizza should be gone");
        assertTrue(pizzaRepository.existsById(managedPizza.getIdPizza()), "Pizza should remain");
    }
}