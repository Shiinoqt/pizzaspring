package com.spring.pizzaspring.repository;

import com.spring.pizzaspring.model.Cliente;
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
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void shouldSaveAndFindCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Luigi Verdi");
        cliente.setIndirizzo("Via Napoli 5");
        cliente.setTelefono("987654");

        Cliente savedCliente = clienteRepository.save(cliente);
        Optional<Cliente> found = clienteRepository.findById(savedCliente.getIdCliente());

        assertTrue(found.isPresent());
        assertEquals("Luigi Verdi", found.get().getNome());
        assertNotNull(found.get().getIdCliente());
    }

    @Test
    void shouldUpdateCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Original Name");
        cliente.setIndirizzo("Via Roma 1");
        cliente.setTelefono("123456");
        clienteRepository.save(cliente);

        cliente.setNome("Updated Name");
        clienteRepository.save(cliente);

        Cliente updated = clienteRepository.findById(cliente.getIdCliente()).get();

        assertEquals("Updated Name", updated.getNome());
    }

    @Test
    void shouldDeleteCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("To Be Deleted");
        cliente.setIndirizzo("Via Roma 10");
        cliente.setTelefono("123456");
        clienteRepository.save(cliente);
        Long id = cliente.getIdCliente();

        clienteRepository.deleteById(id);

        assertFalse(clienteRepository.existsById(id));
    }
}