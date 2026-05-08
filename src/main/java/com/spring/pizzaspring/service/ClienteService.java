package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.ClienteDTO;

import java.util.Collection;

public interface ClienteService {
    ClienteDTO registraCliente(ClienteDTO clienteDTO);
    ClienteDTO updateCliente(Long id, ClienteDTO newClienteDTO);
    ClienteDTO getClienteById(Long id);
    Collection<ClienteDTO> selectAll();
    void deleteCliente(Long id);
}
