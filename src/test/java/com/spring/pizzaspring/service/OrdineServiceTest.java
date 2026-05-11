package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.dto.OrdinePrioritarioDTO;
import com.spring.pizzaspring.mapper.OrdineMapper;
import com.spring.pizzaspring.mapper.OrdinePrioritarioMapper;
import com.spring.pizzaspring.model.*;
import com.spring.pizzaspring.repository.OrdineRepository;
import com.spring.pizzaspring.repository.RiderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
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

        verify(ordineRepository).save(entity);
    }

    @Test
    void creaOrdinePrioritario_ShouldSaveSuccessfully() {
        OrdinePrioritarioDTO dto = new OrdinePrioritarioDTO();
        OrdinePrioritario entity = new OrdinePrioritario();
        when(ordinePrioritarioMapper.DTOToOrdineprioritario(dto)).thenReturn(entity);

        service.creaOrdinePrioritario(dto);

        verify(ordineRepository).save(entity);
    }

    @Test
    void calcoloTotale_ShouldSumPizzasThroughLinkEntity() {
        String codice = "ORD-123";
        Ordine ordine = new Ordine();

        Pizza p1 = new Pizza();
        p1.setPrezzo(10.0);
        Pizza p2 = new Pizza();
        p2.setPrezzo(5.0);

        OrdinePizza link1 = new OrdinePizza();
        link1.setPizza(p1);
        link1.setQuantita(1);

        OrdinePizza link2 = new OrdinePizza();
        link2.setPizza(p2);
        link2.setQuantita(3);

        ordine.setPizzeOrdinate(List.of(link1, link2));

        when(ordineRepository.findById(codice)).thenReturn(Optional.of(ordine));

        Double result = service.calcoloTotale(codice);

        assertEquals(25.0, result);
    }

    @Test
    void calcoloTotale_ShouldIncludeSurchargeForPriorityOrder() {
        String codice = "PRIO-123";

        OrdinePrioritario ordinePrio = new OrdinePrioritario();
        ordinePrio.setSovrapprezzo(3.0);

        Pizza p1 = new Pizza();
        p1.setPrezzo(10.0);

        OrdinePizza link = new OrdinePizza();
        link.setPizza(p1);
        link.setQuantita(1);

        ordinePrio.setPizzeOrdinate(List.of(link));

        when(ordineRepository.findById(codice)).thenReturn(Optional.of(ordinePrio));

        Double result = service.calcoloTotale(codice);

        assertEquals(13.0, result);
    }

    @Test
    void getDettaglioPizze_ShouldReturnCorrectQuantities() {
        String codice = "ORD-123";
        Ordine ordine = new Ordine();

        Pizza p1 = new Pizza();
        p1.setNome("Margherita");

        Pizza p2 = new Pizza();
        p2.setNome("Diavola");

        OrdinePizza link1 = new OrdinePizza();
        link1.setPizza(p1);
        link1.setQuantita(2);

        OrdinePizza link2 = new OrdinePizza();
        link2.setPizza(p2);
        link2.setQuantita(1);

        ordine.setPizzeOrdinate(List.of(link1, link2));
        when(ordineRepository.findById(codice)).thenReturn(Optional.of(ordine));

        Map<String, Integer> dettaglio = service.getDettaglioPizze(codice);
        System.out.println(dettaglio);

        assertEquals(2, dettaglio.get("Margherita"));
        assertEquals(1, dettaglio.get("Diavola"));
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