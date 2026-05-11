package com.spring.pizzaspring.mapper;

import com.spring.pizzaspring.dto.ClienteDTO;
import com.spring.pizzaspring.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrdineMapper.class})
public interface ClienteMapper {
    ClienteDTO clienteToDTO(Cliente cliente);
    
    @Mapping(target = "ordini", ignore = true)
    Cliente DTOToCliente(ClienteDTO clienteDTO);
}
