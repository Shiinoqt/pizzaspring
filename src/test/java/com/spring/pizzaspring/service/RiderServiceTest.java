package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.RiderDTO;
import com.spring.pizzaspring.mapper.RiderMapper;
import com.spring.pizzaspring.model.Rider;
import com.spring.pizzaspring.repository.RiderRepository;
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
public class RiderServiceTest {

    @Mock
    private RiderRepository riderRepository;

    @Mock
    private RiderMapper riderMapper;

    @InjectMocks
    private RiderServiceImpl riderService;

    private Rider riderEntity;
    private RiderDTO riderDto;

    @BeforeEach
    void setUp() {
        riderEntity = new Rider();
        riderEntity.setIdRider(1L);
        riderEntity.setNome("Marco Bici");

        riderDto = new RiderDTO();
        riderDto.setIdRider(1L);
        riderDto.setNome("Marco Bici");
    }

    @Test
    void registraRider_ShouldReturnSavedRiderDTO() {
        when(riderMapper.DTOToRider(any(RiderDTO.class))).thenReturn(riderEntity);
        when(riderRepository.save(any(Rider.class))).thenReturn(riderEntity);
        when(riderMapper.riderToDTO(any(Rider.class))).thenReturn(riderDto);

        RiderDTO result = riderService.registraRider(riderDto);

        assertNotNull(result);
        assertEquals("Marco Bici", result.getNome());
        verify(riderRepository, times(1)).save(riderEntity);
    }

    @Test
    void getRiderById_ShouldReturnRiderDTO_WhenExists() {
        when(riderRepository.findById(1L)).thenReturn(Optional.of(riderEntity));
        when(riderMapper.riderToDTO(riderEntity)).thenReturn(riderDto);

        RiderDTO result = riderService.getRiderById(1L);

        assertEquals(1L, result.getIdRider());
        verify(riderRepository).findById(1L);
    }

    @Test
    void getRiderById_ShouldThrowException_WhenNotExists() {
        when(riderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> riderService.getRiderById(1L));
    }

    @Test
    void updateRider_ShouldReturnUpdatedDTO() {
        RiderDTO updateInfo = new RiderDTO();
        updateInfo.setNome("Marco Moto");

        when(riderRepository.findById(1L)).thenReturn(Optional.of(riderEntity));
        when(riderRepository.save(any(Rider.class))).thenReturn(riderEntity);
        when(riderMapper.riderToDTO(any(Rider.class))).thenReturn(updateInfo);

        RiderDTO result = riderService.updateRider(1L, updateInfo);

        assertEquals("Marco Moto", result.getNome());
        verify(riderRepository).save(riderEntity);
    }

    @Test
    void selectAll_ShouldReturnList() {
        when(riderRepository.findAll()).thenReturn(List.of(riderEntity));
        when(riderMapper.riderToDTO(riderEntity)).thenReturn(riderDto);

        var results = riderService.selectAll();

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    void deleteRider_ShouldInvokeDelete_WhenExists() {
        when(riderRepository.existsById(1L)).thenReturn(true);

        riderService.deleteRider(1L);

        verify(riderRepository).deleteById(1L);
    }
}