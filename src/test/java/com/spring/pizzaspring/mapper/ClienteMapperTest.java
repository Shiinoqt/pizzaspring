package com.spring.pizzaspring.mapper; // Same package as the Mapper

import com.spring.pizzaspring.model.Cliente;
import com.spring.pizzaspring.dto.ClienteDTO;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class ClienteMapperTest {

    // Implements mapstruct
    private final ClienteMapper mapper = Mappers.getMapper(ClienteMapper.class);

    @Test
    void shouldMapClienteToDto() {
        Cliente entity = new Cliente();
        entity.setIdCliente(1L);
        entity.setNome("Mario Rossi");
        entity.setIndirizzo("Via Roma 1");
        entity.setTelefono("123456");
        System.out.println("Cliente 1 creato");
        System.out.println(entity.getNome());

        Cliente entity2 = new Cliente(2L, "Pippo", "Via Roma 1", "50223", null);
        System.out.println("Cliente 2 creato");
        System.out.println(entity2.toString());

        ClienteDTO dto = mapper.clienteToDTO(entity);
        System.out.println("Cliente convertito a DTO");

        assertNotNull(dto, "The DTO should not be null");
        assertEquals(entity.getIdCliente(), dto.getIdCliente());
        assertEquals(entity.getNome(), dto.getNome());
        assertEquals(entity.getIndirizzo(), dto.getIndirizzo());
    }
}