package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.ClienteDTO;
import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.dto.OrdinePizzaDTO;
import com.spring.pizzaspring.dto.OrdinePrioritarioDTO;
import com.spring.pizzaspring.mapper.OrdineMapper;
import com.spring.pizzaspring.mapper.OrdinePrioritarioMapper;
import com.spring.pizzaspring.model.*;
import com.spring.pizzaspring.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    private PizzaRepository pizzaRepository;

    @Mock
    private OrdinePizzaRepository ordinePizzaRepository;

    @Mock
    private ClienteRepository clienteRepository;

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

    private Cliente clienteEntity;
    private ClienteDTO clienteDto;

    // Initializes reusable "Cliente" objects before every test
    @BeforeEach
    void setUp() {
        clienteEntity = new Cliente();
        clienteEntity.setIdCliente(1L);
        clienteEntity.setNome("Mario Rossi");
        clienteEntity.setIndirizzo("Via Roma 1");

        clienteDto = new ClienteDTO();
        clienteDto.setIdCliente(1L);
        clienteDto.setNome("Mario Rossi");
        clienteDto.setIndirizzo("Via Roma 1");
    }
    @Test
    @DisplayName("Should successfully save a standard order and its pizzas")
    void creaOrdine() {
        Long clienteId = 1L;
        Long pizzaId = 10L;

        OrdinePizzaDTO pizzaDto = new OrdinePizzaDTO();
        pizzaDto.setIdPizza(pizzaId);
        pizzaDto.setQuantita(2);

        OrdineDTO dto = new OrdineDTO();
        dto.setIdCliente(clienteId);
        dto.setPizzeOrdinate(List.of(pizzaDto));

        Ordine entity = new Ordine();
        Pizza pizzaEntity = new Pizza();

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteEntity));
        when(ordineMapper.DTOToOrdine(dto)).thenReturn(entity);
        when(ordineRepository.save(entity)).thenReturn(entity);
        when(pizzaRepository.findById(pizzaId)).thenReturn(Optional.of(pizzaEntity));

        service.creaOrdine(dto);

        verify(ordineRepository, times(1)).save(entity);
        verify(ordinePizzaRepository, times(1)).save(any(OrdinePizza.class));
        assertEquals(clienteEntity, entity.getCliente(), "The entity should have client assigned");
        verify(clienteRepository).findById(clienteId);
        verify(pizzaRepository).findById(pizzaId);
    }

    @Test
    @DisplayName("Should successfully save a priority order and its pizzas")
    void creaOrdinePrioritario() {
        Long clienteId = 1L;
        Long pizzaId = 20L;

        OrdinePizzaDTO pizzaDto = new OrdinePizzaDTO();
        pizzaDto.setIdPizza(pizzaId);
        pizzaDto.setQuantita(1);

        OrdinePrioritarioDTO dto = new OrdinePrioritarioDTO();
        dto.setIdCliente(clienteId);
        dto.setSovrapprezzo(3.0);
        dto.setPizzeOrdinate(List.of(pizzaDto));

        OrdinePrioritario entity = new OrdinePrioritario();
        Pizza pizzaEntity = new Pizza();

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteEntity));
        when(ordinePrioritarioMapper.DTOToOrdineprioritario(dto)).thenReturn(entity);
        when(ordineRepository.save(entity)).thenReturn(entity);
        when(pizzaRepository.findById(pizzaId)).thenReturn(Optional.of(pizzaEntity));

        service.creaOrdinePrioritario(dto);

        verify(ordineRepository, times(1)).save(entity);
        verify(ordinePizzaRepository, times(1)).save(any(OrdinePizza.class));
        assertEquals(clienteEntity, entity.getCliente(), "The priority order should be linked to the correct client");
        verify(clienteRepository).findById(clienteId);
        verify(pizzaRepository).findById(pizzaId);
    }

    @Test
    @DisplayName("Should correctly calculate total price by summing pizzas through the link entity")
    void calcoloTotale() {
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
    @DisplayName("Should include the priority surcharge in the total price for priority orders")
    void calcoloTotalePrio() {
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
    @DisplayName("Should return a map of pizza names and quantities for a specific order")
    void getDettaglioPizze() {
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

        assertNotNull(dettaglio);
        assertEquals(2, dettaglio.get("Margherita"));
        assertEquals(1, dettaglio.get("Diavola"));
    }

    @Test
    @DisplayName("Should assign a rider to an order and persist the change")
    void assegnaRider() {
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
    @DisplayName("Should throw RuntimeException when attempting to assign a non-existent rider")
    void assegnaRiderNonEsistente() {
        String codice = "ORD-123";
        when(ordineRepository.findById(codice)).thenReturn(Optional.of(new Ordine()));
        when(riderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.assegnaRider(codice, 1L));
        verify(ordineRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should return order DTO when ID is found")
    void getOrdineById() {
        String codice = "ABC";
        Ordine entity = new Ordine();
        OrdineDTO dto = new OrdineDTO();

        when(ordineRepository.findById(codice)).thenReturn(Optional.of(entity));
        when(ordineMapper.ordineToDTO(entity)).thenReturn(dto);

        OrdineDTO result = service.getOrdineById(codice);

        assertNotNull(result);
        verify(ordineRepository).findById(codice);
        verify(ordineMapper).ordineToDTO(entity);
    }

    @Test
    @DisplayName("Should invoke delete on repository when order exists")
    void deleteOrdine() {
        String id = "ORD-001";
        when(ordineRepository.existsById(id)).thenReturn(true);

        service.deleteOrdine(id);

        verify(ordineRepository).existsById(id);
        verify(ordineRepository).deleteById(id);
    }
}