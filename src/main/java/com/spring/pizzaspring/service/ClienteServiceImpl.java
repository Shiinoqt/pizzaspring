package com.spring.pizzaspring.service;

import com.spring.pizzaspring.component.SaveOrdiniPizzaComponent;
import com.spring.pizzaspring.dto.ClienteDTO;
import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.dto.OrdinePizzaDTO;
import com.spring.pizzaspring.exceptions.InvalidOrderException;
import com.spring.pizzaspring.exceptions.NotFoundException;
import com.spring.pizzaspring.exceptions.ResourceAlreadyExistsException;
import com.spring.pizzaspring.mapper.ClienteMapper;
import com.spring.pizzaspring.mapper.OrdineMapper;
import com.spring.pizzaspring.model.Cliente;
import com.spring.pizzaspring.model.Ordine;
import com.spring.pizzaspring.model.OrdinePizza;
import com.spring.pizzaspring.model.Pizza;
import com.spring.pizzaspring.repository.ClienteRepository;
import com.spring.pizzaspring.repository.OrdinePizzaRepository;
import com.spring.pizzaspring.repository.OrdineRepository;
import com.spring.pizzaspring.repository.PizzaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService{
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private OrdineRepository ordineRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private OrdineMapper ordineMapper;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private OrdinePizzaRepository ordinePizzaRepository;

    @Autowired
    private SaveOrdiniPizzaComponent saveOrdiniPizzaComponent;

    @Override
    @Transactional
    public ClienteDTO registraCliente(ClienteDTO clienteDTO) {
        if (clienteDTO.getIdCliente() != null && clienteRepository.existsById(clienteDTO.getIdCliente())) {
            throw new ResourceAlreadyExistsException("Client ID already registered.");
        }

        if (clienteRepository.existsByTelefono(clienteDTO.getTelefono())) {
            throw new ResourceAlreadyExistsException("Phone number already registered.");
        }

        if (clienteDTO.getOrdini() == null || clienteDTO.getOrdini().isEmpty()) {
            throw new InvalidOrderException("Can't register client without orders.");
        }

        for (OrdineDTO dto : clienteDTO.getOrdini()) {
            if (dto.getPizzeOrdinate() == null || dto.getPizzeOrdinate().isEmpty()) {
                throw new InvalidOrderException("Order must have one pizza.");
            }
        }

        // Mapping and saving cliente
        Cliente cliente = clienteMapper.DTOToCliente(clienteDTO);
        Cliente savedCliente = clienteRepository.save(cliente);

        // For each ordine in the cliente set it and saves it to this
        for (OrdineDTO dto : clienteDTO.getOrdini()) {
            Ordine ordine = ordineMapper.DTOToOrdine(dto);

            ordine.setCliente(savedCliente);
            Ordine savedOrdine = ordineRepository.save(ordine);

            saveOrdiniPizzaComponent.saveOrdiniPizza(savedOrdine, dto.getPizzeOrdinate());
        }

        return clienteMapper.clienteToDTO(savedCliente);
    }

    @Override
    public ClienteDTO updateCliente(Long id, ClienteDTO newClienteDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client ID not found."));

        cliente.setNome(newClienteDTO.getNome());
        cliente.setIndirizzo(newClienteDTO.getIndirizzo());
        cliente.setTelefono(newClienteDTO.getTelefono());

        Cliente savedCliente = clienteRepository.save(cliente);

        return clienteMapper.clienteToDTO(savedCliente);
    }

    @Override
    public ClienteDTO getClienteById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found."));
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
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new NotFoundException("Client not found."));

        return cliente.getOrdini().stream()
                .map(ordine -> ordineMapper.ordineToDTO(ordine))
                .toList();
    }

    @Override
    @Transactional
    public void deleteCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new NotFoundException("Cannot delete: Client not found.");
        }
        clienteRepository.deleteById(id);
    }
}
