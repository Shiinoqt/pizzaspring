package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.ClienteDTO;
import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.mapper.ClienteMapper;
import com.spring.pizzaspring.mapper.OrdineMapper;
import com.spring.pizzaspring.model.Cliente;
import com.spring.pizzaspring.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService{
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private OrdineMapper ordineMapper;

    private Long clienteID;

    @Override
    @Transactional
    public ClienteDTO registraCliente(ClienteDTO clienteDTO) {
        Cliente cliente = clienteMapper.DTOToCliente(clienteDTO);
        Cliente savedCliente = clienteRepository.save(cliente);
        return clienteMapper.clienteToDTO(savedCliente);
    }

    @Override
    public ClienteDTO updateCliente(Long id, ClienteDTO newClienteDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente not found."));

        cliente.setNome(newClienteDTO.getNome());
        cliente.setIndirizzo(newClienteDTO.getIndirizzo());
        cliente.setTelefono(newClienteDTO.getTelefono());

        Cliente savedCliente = clienteRepository.save(cliente);

        return clienteMapper.clienteToDTO(cliente);
    }

    @Override
    public ClienteDTO getClienteById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente not found."));
        return clienteMapper.clienteToDTO(cliente);
    }

    @Override
    public Collection<ClienteDTO> selectAll() {
        return clienteRepository.findAll()
                .stream()
                .map(clienteMapper::clienteToDTO)
                .toList();
    }

    @Override
    public Collection<OrdineDTO> getOrdiniByCliente(Long idCliente) {
        // 1. Fetch the entity
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente non trovato"));

        // 2. Map the internal entities to DTOs
        return cliente.getOrdini().stream()
                .map(ordine -> ordineMapper.ordineToDTO(ordine))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete: Cliente not found");
        }
        clienteRepository.deleteById(id);
    }
}
