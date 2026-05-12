package com.spring.pizzaspring.repository;

import com.spring.pizzaspring.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AssociationTest {
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
    void clienteShouldHaveAtLeastOneOrdine() {
        Cliente cliente = new Cliente();
        cliente.setNome("Mario");
        cliente.setIndirizzo("Via Roma 1");
        cliente.setTelefono("123");
        cliente.setOrdini(new ArrayList<>());

        Ordine ordine = new Ordine();
        ordine.setCodice("ORD-001");
        ordine.setCliente(cliente);
        cliente.getOrdini().add(ordine);

        Cliente saved = clienteRepository.saveAndFlush(cliente);
        assertFalse(saved.getOrdini().isEmpty(), "Cliente must have at least one ordine");
        assertEquals(1, saved.getOrdini().size());
    }

    @Test
    void ordineShouldLinkMultiplePizze() {
        Cliente cliente = new Cliente();
        cliente.setNome("Mario Rossi");
        cliente.setIndirizzo("Via Roma 1");
        cliente.setTelefono("12345");
        Cliente savedCliente = clienteRepository.saveAndFlush(cliente);

        Pizza p1=new Pizza();
        p1.setNome("Margherita");
        p1.setDescrizione("Test Desc");
        p1.setPrezzo(5.0);
        pizzaRepository.save(p1);

        Pizza p2=new Pizza();
        p2.setNome("Diavola");
        p2.setDescrizione("Test Desc");
        p2.setPrezzo(5.0);
        pizzaRepository.save(p2);

        Ordine ordine = new Ordine();
        ordine.setCodice("ORD-MULTI");
        ordine.setCliente(savedCliente);
        ordine.setPizzeOrdinate(new ArrayList<>());
        Ordine managedOrdine = ordineRepository.saveAndFlush(ordine);

        OrdinePizza op1 = new OrdinePizza();
        op1.setOrdine(managedOrdine); op1.setPizza(p1); op1.setQuantita(2);

        OrdinePizza op2 = new OrdinePizza();
        op2.setOrdine(managedOrdine); op2.setPizza(p2); op2.setQuantita(1);

        managedOrdine.getPizzeOrdinate().addAll(List.of(op1, op2));
        ordineRepository.saveAndFlush(managedOrdine);

        Ordine found = ordineRepository.findById("ORD-MULTI").orElseThrow();
        assertEquals(2, found.getPizzeOrdinate().size());
    }

    @Test
    void riderShouldBeOptionalOnOrdine() {
        Cliente cliente = new Cliente();
        cliente.setNome("Mario Rossi");
        cliente.setIndirizzo("Via Roma 1");
        cliente.setTelefono("12345");
        Cliente savedCliente = clienteRepository.saveAndFlush(cliente);

        Ordine ordine = new Ordine();
        ordine.setCodice("ORD-NORIDER");
        ordine.setCliente(savedCliente);
        ordineRepository.saveAndFlush(ordine);

        Ordine found = ordineRepository.findById("ORD-NORIDER").orElseThrow();
        assertNull(found.getRider(), "Rider should be optional");

        // Now assign a rider
        Rider rider = new Rider();
        rider.setNome("Speedy");
        riderRepository.save(rider);

        found.setRider(rider);
        ordineRepository.saveAndFlush(found);

        Ordine updated = ordineRepository.findById("ORD-NORIDER").orElseThrow();
        assertNotNull(updated.getRider());
    }

    @Test
    void ordinePrioritarioShouldBeInstanceOfOrdine() {
        Cliente cliente = new Cliente();
        cliente.setNome("Mario Rossi");
        cliente.setIndirizzo("Via Roma 1");
        cliente.setTelefono("12345");
        Cliente savedCliente = clienteRepository.saveAndFlush(cliente);

        OrdinePrioritario op = new OrdinePrioritario();
        op.setCodice("PRIO-001");
        op.setCliente(savedCliente);
        op.setSovrapprezzo(3.50);
        ordineRepository.save(op);

        Ordine found = ordineRepository.findById("PRIO-001").orElseThrow();

        assertInstanceOf(OrdinePrioritario.class, found);  // verifies inheritance
        assertEquals(3.50, ((OrdinePrioritario) found).getSovrapprezzo());
    }
}
