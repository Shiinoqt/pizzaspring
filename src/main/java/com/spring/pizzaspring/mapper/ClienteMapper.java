package com.spring.pizzaspring.mapper;

import com.spring.pizzaspring.dto.ClienteDTO;
import com.spring.pizzaspring.model.Cliente;
import org.mapstruct.Mapper;

@Mapper
public interface ClienteMapper {
    ClienteDTO clienteToDTO(Cliente cliente);
    Cliente DTOToCliente(ClienteDTO clienteDTO);
}
