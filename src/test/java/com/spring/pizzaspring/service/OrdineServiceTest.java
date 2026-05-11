package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.dto.OrdinePrioritarioDTO;
import com.spring.pizzaspring.mapper.OrdineMapper;
import com.spring.pizzaspring.mapper.OrdinePrioritarioMapper;
import com.spring.pizzaspring.model.Ordine;
import com.spring.pizzaspring.model.OrdinePrioritario;
import com.spring.pizzaspring.model.Rider;
import com.spring.pizzaspring.repository.OrdineRepository;
import com.spring.pizzaspring.repository.RiderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrdineServiceTest {

    @Mock
    private OrdineRepository ordineRepository;

    @Mock
    private RiderRepository riderRepository;

    @Mock
    private OrdineMapper ordineMapper;

    @Mock
    private OrdinePrioritarioMapper ordinePrioritarioMapper;

    @InjectMocks
    private OrdineServiceImpl service;

    @Test
    void creaOrdine_ShouldSaveSuccessfully() {
        OrdineDTO dto = new OrdineDTO();
        Ordine entity = new Ordine();
        when(ordineMapper.DTOToOrdine(dto)).thenReturn(entity);

        service.creaOrdine(dto);

        verify(ordineRepository, times(1)).save(entity);
    }

    @Test
    void creaOrdinePrioritario_ShouldSaveSuccessfully() {
        OrdinePrioritarioDTO dto = new OrdinePrioritarioDTO();
        OrdinePrioritario entity = new OrdinePrioritario();
        when(ordinePrioritarioMapper.DTOToOrdineprioritario(dto)).thenReturn(entity);

        service.creaOrdinePrioritario(dto);

        verify(ordineRepository, times(1)).save(entity);
    }

    @Test
    void assegnaRider_ShouldUpdateOrdineWithRider() {
        String codice = "ORD-123";
        Long riderId = 1L;

        Ordine ordine = new Ordine();
        Rider rider = new Rider();

        when(ordineRepository.findById(codice)).thenReturn(Optional.of(ordine));
        when(riderRepository.findById(riderId)).thenReturn(Optional.of(rider));

        service.assegnaRider(codice, riderId);

        assertNotNull(ordine.getRider());
        assertEquals(rider, ordine.getRider());
        verify(ordineRepository).save(ordine);
    }

    @Test
    void assegnaRider_ShouldThrowException_WhenRiderNotFound() {
        String codice = "ORD-123";
        when(ordineRepository.findById(codice)).thenReturn(Optional.of(new Ordine()));
        when(riderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.assegnaRider(codice, 1L));
        verify(ordineRepository, never()).save(any());
    }

    @Test
    void getOrdineById_ShouldReturnDTO() {
        String codice = "ABC";
        Ordine entity = new Ordine();
        OrdineDTO dto = new OrdineDTO();

        when(ordineRepository.findById(codice)).thenReturn(Optional.of(entity));
        when(ordineMapper.ordineToDTO(entity)).thenReturn(dto);


        OrdineDTO result = service.getOrdineById(codice);

        assertNotNull(result);
        verify(ordineRepository).findById(codice);
    }

    @Test
    void deleteOrdine_ShouldInvokeDelete_WhenExists() {
        String id = "ORD-001";
        when(ordineRepository.existsById(id)).thenReturn(true);

        service.deleteOrdine(id);

        verify(ordineRepository).deleteById(id);
    }
}