package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.ClienteDTO;
import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.mapper.ClienteMapper;
import com.spring.pizzaspring.mapper.OrdineMapper;
import com.spring.pizzaspring.model.Cliente;
import com.spring.pizzaspring.model.Ordine;
import com.spring.pizzaspring.repository.ClienteRepository;
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

// Enables Mockito
@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private OrdineMapper ordineMapper; // Add this mock

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    // We created the mocks of the dependencies used in "ClienteServiceImpl" and inject it into real service
    @InjectMocks
    private ClienteServiceImpl clienteService;

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
    @DisplayName("Should successfully register a client and return the DTO")
    void registraCliente() {
        // Simulating Mapper conversions and repository save
        when(clienteMapper.DTOToCliente(any(ClienteDTO.class))).thenReturn(clienteEntity);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteEntity);
        when(clienteMapper.clienteToDTO(any(Cliente.class))).thenReturn(clienteDto);

        // Registering Cliente
        ClienteDTO result = clienteService.registraCliente(clienteDto);

        // Assert
        assertNotNull(result);
        assertEquals("Mario Rossi", result.getNome());

        // Check if save() was called
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Should update client details and return the updated DTO")
    void updateCliente() {
        // Preparing info to update
        Long id = 1L;
        ClienteDTO updateInfo = new ClienteDTO();
        updateInfo.setNome("Mario Bianchi");

        // Simulating when it finds it, saves it, and returns DTO
        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteEntity));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteEntity);
        when(clienteMapper.clienteToDTO(any(Cliente.class))).thenReturn(updateInfo);

        // Updating
        ClienteDTO result = clienteService.updateCliente(id, updateInfo);

        // Assert
        assertNotNull(result);
        assertEquals("Mario Bianchi", result.getNome());

        // Check if "findbyid" and save was called
        verify(clienteRepository).findById(id);
        verify(clienteRepository).save(clienteEntity);
    }

    @Test
    @DisplayName("Should return ClienteDTO when a valid ID is provided")
    void getClienteById() {
        // Simulating finding by id and returning DTO
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteEntity));
        when(clienteMapper.clienteToDTO(clienteEntity)).thenReturn(clienteDto);

        ClienteDTO result = clienteService.getClienteById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getIdCliente());
        verify(clienteRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw RuntimeException when client ID does not exist")
    void getClienteByIdNonTrovato() {
        // Simulating find by id that returns nothing
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        // Testing if it runs into exception
        assertThrows(RuntimeException.class, () -> clienteService.getClienteById(1L));
        verify(clienteMapper, never()).clienteToDTO(any());
    }

    @Test
    @DisplayName("Should return a collection of all clients")
    void selectAll() {
        // Simulating findall() returns
        when(clienteRepository.findAll()).thenReturn(List.of(clienteEntity));
        when(clienteMapper.clienteToDTO(clienteEntity)).thenReturn(clienteDto);

        Collection<ClienteDTO> results = clienteService.selectAll();

        // Assert
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        verify(clienteRepository).findAll();
    }

    @Test
    @DisplayName("Should return a list of order DTOs for a specific client")
    void getOrdiniByCliente() {
        Long id = 1L;

        Ordine o1 = new Ordine();
        o1.setCodice("ORD-1");
        clienteEntity.setOrdini(List.of(o1));

        OrdineDTO ordineDto = new OrdineDTO();
        ordineDto.setCodice("ORD-1");

        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteEntity));
        when(ordineMapper.ordineToDTO(o1)).thenReturn(ordineDto);

        Collection<OrdineDTO> results = clienteService.getOrdiniByCliente(id);

        // Assert
        assertNotNull(results);
        assertEquals(1, results.size());
        verify(clienteRepository).findById(id);
        verify(ordineMapper, atLeastOnce()).ordineToDTO(any());
    }

    @Test
    @DisplayName("Should successfully delete a client when they exist")
    void deleteCliente() {
        Long id = 1L;
        when(clienteRepository.existsById(id)).thenReturn(true);

        clienteService.deleteCliente(id);

        verify(clienteRepository).existsById(id);
        verify(clienteRepository).deleteById(id);
    }

    @Test
    @DisplayName("Should throw exception and skip deletion when client does not exist")
    void deleteClienteNonEsistente() {
        // Simulating find by id that returns nothing
        when(clienteRepository.existsById(1L)).thenReturn(false);

        // Testing if it runs into exception
        assertThrows(RuntimeException.class, () -> clienteService.deleteCliente(1L));

        // Verify delete was never called
        verify(clienteRepository).existsById(1L);
        verify(clienteRepository, never()).deleteById(anyLong());
    }
}