package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.dto.RiderDTO;
import com.spring.pizzaspring.mapper.OrdineMapper;
import com.spring.pizzaspring.mapper.RiderMapper;
import com.spring.pizzaspring.model.Ordine;
import com.spring.pizzaspring.model.Rider;
import com.spring.pizzaspring.repository.RiderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RiderServiceTest {
    @Mock
    private RiderRepository riderRepository;

    @Mock
    private RiderMapper riderMapper;

    @Mock
    private OrdineMapper ordineMapper;

    @InjectMocks
    private RiderServiceImpl riderService;

    private Rider riderEntity;
    private RiderDTO riderDto;

    @BeforeEach
    void setUp() {
        riderEntity = new Rider();
        riderEntity.setIdRider(1L);
        riderEntity.setNome("Marco Bici");
        riderEntity.setOrdini(List.of(new Ordine()));

        riderDto = new RiderDTO();
        riderDto.setIdRider(1L);
        riderDto.setNome("Marco Bici");
    }

    @Test
    @DisplayName("Should successfully register a new rider")
    void registraRider() {
        when(riderMapper.DTOToRider(any(RiderDTO.class))).thenReturn(riderEntity);
        when(riderRepository.save(any(Rider.class))).thenReturn(riderEntity);
        when(riderMapper.riderToDTO(any(Rider.class))).thenReturn(riderDto);

        RiderDTO result = riderService.registraRider(riderDto);

        assertNotNull(result);
        assertEquals(riderDto.getNome(), result.getNome());
        verify(riderRepository, times(1)).save(any(Rider.class));
    }

    @Test
    @DisplayName("Should return a RiderDTO when valid ID is provided")
    void getRiderById() {
        when(riderRepository.findById(1L)).thenReturn(Optional.of(riderEntity));
        when(riderMapper.riderToDTO(riderEntity)).thenReturn(riderDto);

        RiderDTO result = riderService.getRiderById(1L);

        assertEquals(1L, result.getIdRider());
        verify(riderRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw RuntimeException when rider ID does not exist")
    void getRiderByIdNonEsistente() {
        when(riderRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> riderService.getRiderById(1L));
        assertEquals("Rider non trovato", exception.getMessage());
    }

    @Test
    @DisplayName("Should update rider name and return updated DTO")
    void updateRider() {
        RiderDTO updateInfo = new RiderDTO();
        updateInfo.setNome("Marco Moto");

        when(riderRepository.findById(1L)).thenReturn(Optional.of(riderEntity));
        when(riderRepository.save(any(Rider.class))).thenReturn(riderEntity);
        when(riderMapper.riderToDTO(riderEntity)).thenReturn(updateInfo);

        RiderDTO result = riderService.updateRider(1L, updateInfo);

        assertEquals("Marco Moto", result.getNome());
        verify(riderRepository).save(riderEntity);
    }

    @Test
    @DisplayName("Should return a list of orders for a specific rider")
    void getOrdiniByRider() {
        when(riderRepository.findById(1L)).thenReturn(Optional.of(riderEntity));
        when(ordineMapper.ordineToDTO(any())).thenReturn(new OrdineDTO());

        Collection<OrdineDTO> result = riderService.getOrdiniByRider(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(ordineMapper, atLeastOnce()).ordineToDTO(any());
    }

    @Test
    @DisplayName("Should return all riders")
    void selectAll() {
        when(riderRepository.findAll()).thenReturn(List.of(riderEntity));
        when(riderMapper.riderToDTO(riderEntity)).thenReturn(riderDto);

        Collection<RiderDTO> results = riderService.selectAll();

        assertEquals(1, results.size());
        verify(riderRepository).findAll();
    }

    @Test
    @DisplayName("Should delete rider when ID exists")
    void deleteRider() {
        when(riderRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> riderService.deleteRider(1L));

        verify(riderRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent rider")
    void deleteRiderNonEsistente() {
        when(riderRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> riderService.deleteRider(1L));
        verify(riderRepository, never()).deleteById(anyLong());
    }
}