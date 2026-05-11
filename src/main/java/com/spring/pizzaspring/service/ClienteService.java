package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.ClienteDTO;
import com.spring.pizzaspring.dto.OrdineDTO;

import java.util.Collection;

public interface ClienteService {
    ClienteDTO registraCliente(ClienteDTO clienteDTO);
    ClienteDTO updateCliente(Long id, ClienteDTO newClienteDTO);
    ClienteDTO getClienteById(Long id);
    Collection<ClienteDTO> selectAll();
    Collection<OrdineDTO> getOrdiniByCliente(Long idCliente);
    void deleteCliente(Long id);
}
