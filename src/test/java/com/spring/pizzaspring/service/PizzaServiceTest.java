package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.PizzaDTO;
import com.spring.pizzaspring.mapper.PizzaMapper;
import com.spring.pizzaspring.model.Pizza;
import com.spring.pizzaspring.repository.PizzaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PizzaServiceTest {

    @Mock
    private PizzaRepository pizzaRepository;

    @Mock
    private PizzaMapper pizzaMapper;

    @InjectMocks
    private PizzaServiceImpl pizzaService;

    private Pizza pizzaEntity;
    private PizzaDTO pizzaDto;

    @BeforeEach
    void setUp() {
        pizzaEntity = new Pizza();
        pizzaEntity.setIdPizza(1L);
        pizzaEntity.setNome("Margherita");
        pizzaEntity.setDescrizione("Pomodoro e Mozzarella");
        pizzaEntity.setPrezzo(5.50);

        pizzaDto = new PizzaDTO();
        pizzaDto.setIdPizza(1L);
        pizzaDto.setNome("Margherita");
        pizzaDto.setDescrizione("Pomodoro e Mozzarella");
        pizzaDto.setPrezzo(5.50);
    }

    @Test
    void createPizza_ShouldReturnSavedDTO() {
        when(pizzaMapper.DTOToPizza(any(PizzaDTO.class))).thenReturn(pizzaEntity);
        when(pizzaRepository.save(any(Pizza.class))).thenReturn(pizzaEntity);
        when(pizzaMapper.pizzaToDTO(any(Pizza.class))).thenReturn(pizzaDto);

        PizzaDTO result = pizzaService.createPizza(pizzaDto);

        assertNotNull(result);
        assertEquals("Margherita", result.getNome());
        verify(pizzaRepository, times(1)).save(any(Pizza.class));
    }

    @Test
    void updatePizza_ShouldUpdateAndReturnDTO_WhenExists() {
        Long id = 1L;
        PizzaDTO updateData = new PizzaDTO();
        updateData.setNome("Margherita Extra");
        updateData.setPrezzo(7.00);

        when(pizzaRepository.findById(id)).thenReturn(Optional.of(pizzaEntity));
        when(pizzaRepository.save(any(Pizza.class))).thenReturn(pizzaEntity);
        when(pizzaMapper.pizzaToDTO(any(Pizza.class))).thenReturn(updateData);

        PizzaDTO result = pizzaService.updatePizza(id, updateData);

        assertEquals("Margherita Extra", result.getNome());
        assertEquals(7.00, result.getPrezzo());
        verify(pizzaRepository).save(pizzaEntity);
    }

    @Test
    void getPizzaById_ShouldReturnDTO_WhenFound() {
        when(pizzaRepository.findById(1L)).thenReturn(Optional.of(pizzaEntity));
        when(pizzaMapper.pizzaToDTO(pizzaEntity)).thenReturn(pizzaDto);

        PizzaDTO result = pizzaService.getPizzaById(1L);

        assertNotNull(result);
        assertEquals("Margherita", result.getNome());
    }

    @Test
    void getPizzaById_ShouldThrowException_WhenNotFound() {
        when(pizzaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> pizzaService.getPizzaById(1L));
    }

    @Test
    void getAllPizze_ShouldReturnList() {
        when(pizzaRepository.findAll()).thenReturn(List.of(pizzaEntity));
        when(pizzaMapper.pizzaToDTO(pizzaEntity)).thenReturn(pizzaDto);

        var results = pizzaService.getAllPizze();

        assertEquals(1, results.size());
        verify(pizzaRepository).findAll();
    }

    @Test
    void deletePizza_ShouldInvokeDelete_WhenExists() {
        when(pizzaRepository.existsById(1L)).thenReturn(true);

        pizzaService.deletePizza(1L);

        verify(pizzaRepository).deleteById(1L);
    }

    @Test
    void deletePizza_ShouldThrow_WhenNotExists() {
        when(pizzaRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> pizzaService.deletePizza(1L));
        assertEquals("Pizza not found.", exception.getMessage());
        verify(pizzaRepository, never()).deleteById(anyLong());
    }
}