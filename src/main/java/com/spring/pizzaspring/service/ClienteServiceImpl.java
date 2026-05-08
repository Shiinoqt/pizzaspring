package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.ClienteDTO;
import com.spring.pizzaspring.mapper.ClienteMapper;
import com.spring.pizzaspring.model.Cliente;
import com.spring.pizzaspring.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService{

    private ClienteRepository clienteRepository;
    private ClienteMapper clienteMapper;

    @Override
    @Transactional
    public ClienteDTO registraCliente(ClienteDTO clienteDTO) {
        Cliente cliente = clienteMapper.DTOToCliente(clienteDTO);
        Cliente savedCliente = clienteRepository.save(cliente);
        return clienteMapper.clienteToDTO(savedCliente);
    }

    @Override
    public ClienteDTO updateCliente(Long id, ClienteDTO updateDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente not found."));

        cliente.setNome(updateDTO.getNome());
        cliente.setIndirizzo(updateDTO.getIndirizzo());
        cliente.setTelefono(updateDTO.getTelefono());

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
    @Transactional
    public void deleteCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete: Cliente not found");
        }
        clienteRepository.deleteById(id);
    }
}
