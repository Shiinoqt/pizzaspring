package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.ClienteDTO;
import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.dto.OrdinePizzaDTO;
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

    private void saveOrdiniPizza(Ordine ordine, List<OrdinePizzaDTO> pizze) {
        for (OrdinePizzaDTO OPD : pizze) {
            OrdinePizza newOP = new OrdinePizza();
            Pizza pizza = pizzaRepository.findById(OPD.getIdPizza())
                    .orElseThrow(() -> new RuntimeException("Pizza non trovata"));

            newOP.setPizza(pizza);
            newOP.setQuantita(OPD.getQuantita());
            newOP.setOrdine(ordine);

            ordinePizzaRepository.save(newOP);
        }
    }

    @Override
    @Transactional
    public ClienteDTO registraCliente(ClienteDTO clienteDTO) {
        if (clienteDTO.getOrdini() == null || clienteDTO.getOrdini().isEmpty()) {
            throw new IllegalArgumentException("Impossibile registrare un cliente senza ordini");
        }

        for (OrdineDTO dto : clienteDTO.getOrdini()) {
            if (dto.getPizzeOrdinate() == null || dto.getPizzeOrdinate().isEmpty()) {
                throw new IllegalArgumentException("Ogni ordine deve contenere almeno una pizza");
            }
        }

        if (clienteRepository.existsById(clienteDTO.getIdCliente())) {
            throw new IllegalArgumentException("Cliente già registrato");
        }

        // Mapping and saving cliente
        Cliente cliente = clienteMapper.DTOToCliente(clienteDTO);
        Cliente savedCliente = clienteRepository.save(cliente);

        // For each ordine in the cliente set it and saves it to this
        for (OrdineDTO dto : clienteDTO.getOrdini()) {
            Ordine ordine = ordineMapper.DTOToOrdine(dto);
            ordine.setCliente(savedCliente);
            Ordine savedOrdine = ordineRepository.save(ordine);

            saveOrdiniPizza(savedOrdine, dto.getPizzeOrdinate());
        }

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

        return clienteMapper.clienteToDTO(savedCliente);
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
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente non trovato"));

        return cliente.getOrdini().stream()
                .map(ordine -> ordineMapper.ordineToDTO(ordine))
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
