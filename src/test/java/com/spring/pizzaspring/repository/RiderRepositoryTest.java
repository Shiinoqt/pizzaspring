package com.spring.pizzaspring.repository;

import com.spring.pizzaspring.model.Rider;
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
public class RiderRepositoryTest {

    @Autowired
    private RiderRepository riderRepository;

    @Test
    void shouldSaveAndFindRider() {
        Rider rider = new Rider();
        rider.setNome("Marco Rossi");

        Rider savedRider = riderRepository.save(rider);
        Optional<Rider> found = riderRepository.findById(savedRider.getIdRider());

        assertTrue(found.isPresent());
        assertEquals("Marco Rossi", found.get().getNome());
        assertNotNull(savedRider.getIdRider());
    }

    @Test
    void shouldUpdateRider() {
        Rider rider = new Rider();
        rider.setNome("Original Rider");
        riderRepository.save(rider);

        rider.setNome("Updated Rider");
        riderRepository.save(rider);

        Rider updated = riderRepository.findById(rider.getIdRider()).get();

        assertEquals("Updated Rider", updated.getNome());
    }

    @Test
    void shouldDeleteRider() {
        Rider rider = new Rider();
        rider.setNome("To Delete");
        riderRepository.save(rider);
        Long id = rider.getIdRider();

        riderRepository.deleteById(id);

        assertFalse(riderRepository.existsById(id));
    }
}