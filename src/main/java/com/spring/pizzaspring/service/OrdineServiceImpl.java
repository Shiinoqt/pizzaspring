package com.spring.pizzaspring.service;

import com.spring.pizzaspring.component.SaveOrdiniPizzaComponent;
import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.dto.OrdinePizzaDTO;
import com.spring.pizzaspring.dto.OrdinePrioritarioDTO;
import com.spring.pizzaspring.exceptions.InvalidOrderException;
import com.spring.pizzaspring.exceptions.NotFoundException;
import com.spring.pizzaspring.mapper.OrdineMapper;
import com.spring.pizzaspring.mapper.OrdinePizzaMapper;
import com.spring.pizzaspring.mapper.OrdinePrioritarioMapper;
import com.spring.pizzaspring.model.*;
import com.spring.pizzaspring.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrdineServiceImpl implements OrdineService{
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private OrdineRepository ordineRepository;

    @Autowired
    private OrdinePizzaRepository ordinePizzaRepository;

    @Autowired
    private OrdineMapper ordineMapper;

    @Autowired
    private OrdinePrioritarioMapper ordinePrioritarioMapper;

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private SaveOrdiniPizzaComponent saveOrdiniPizzaComponent;

    private Cliente validateOrdine(OrdineDTO dto) {
        // Check id cliente nell'ordine
        if (dto.getIdCliente() == null) {
            throw new InvalidOrderException("Order without client.");
        }

        // Check esistenza del cliente nel db
        Cliente cliente = clienteRepository.findById(dto.getIdCliente())
                .orElseThrow(() -> new NotFoundException("Client not found."));

        // Check presenza pizze
        if (dto.getPizzeOrdinate() == null || dto.getPizzeOrdinate().isEmpty()) {
            throw new InvalidOrderException("Order must have one pizza.");
        }

        return cliente;
    }

    @Value("${ordine.prioritario.sovrapprezzo}")
    private double sovrapprezzo;

    @Override
    @Transactional
    public OrdineDTO creaOrdine(OrdineDTO dto) {
        Cliente cliente = validateOrdine(dto);

        Ordine ordine = ordineMapper.DTOToOrdine(dto);

        ordine.setCliente(cliente);
        Ordine savedOrdine = ordineRepository.save(ordine);

        saveOrdiniPizzaComponent.saveOrdiniPizza(savedOrdine, dto.getPizzeOrdinate());
        return ordineMapper.ordineToDTO(savedOrdine);
    }

    @Override
    @Transactional
    public OrdineDTO creaOrdinePrioritario(OrdinePrioritarioDTO dto) {
        Cliente cliente = validateOrdine(dto);

        OrdinePrioritario ordinePrioritario = ordinePrioritarioMapper.DTOToOrdineprioritario(dto);
        ordinePrioritario.setSovrapprezzo(sovrapprezzo);
        ordinePrioritario.setCliente(cliente);

        OrdinePrioritario saved = ordineRepository.save(ordinePrioritario);
        saveOrdiniPizzaComponent.saveOrdiniPizza(saved, dto.getPizzeOrdinate());

        return OrdinePrioritarioDTO.builder()
                .codice(saved.getCodice())
                .idCliente(cliente.getIdCliente())
                .pizzeOrdinate(dto.getPizzeOrdinate())
                .sovrapprezzo(sovrapprezzo)
                .tipoOrdine("prioritario")
                .build();
    }

    @Override
    public OrdineDTO assegnaRider(String codiceOrdine, Long idRider) {
        Ordine ordine = ordineRepository.findById(codiceOrdine)
                .orElseThrow(() -> new NotFoundException("Order not found."));
        Rider rider = riderRepository.findById(idRider)
                .orElseThrow(() -> new NotFoundException("Rider not found."));
        ordine.setRider(rider);
        ordineRepository.save(ordine);
        return ordineMapper.ordineToDTO(ordine);
    }

    @Override
    @Transactional
    public OrdineDTO modificaOrdine(String codiceOrdine, OrdineDTO newOrdineDTO) {
        Ordine ordine = ordineRepository.findById(codiceOrdine)
                .orElseThrow(() -> new NotFoundException("Order not found."));

        Cliente cliente = validateOrdine(newOrdineDTO);

        ordine.setCliente(cliente);

        ordine.getPizzeOrdinate().clear();
        ordineRepository.saveAndFlush(ordine); // Force the delete of old pizzas

        // Save the new pizza list
        saveOrdiniPizzaComponent.saveOrdiniPizza(ordine, newOrdineDTO.getPizzeOrdinate());
        return ordineMapper.ordineToDTO(ordine);
    }
    @Override
    @Transactional
    public OrdineDTO patchPizzeOnly(String codiceOrdine, List<OrdinePizzaDTO> nuovePizze) {
        Ordine ordine = ordineRepository.findById(codiceOrdine)
                .orElseThrow(() -> new NotFoundException("Order not found."));

        if (nuovePizze == null || nuovePizze.isEmpty()) {
            throw new InvalidOrderException("Order must have one pizza.");
        }

        ordine.getPizzeOrdinate().clear();
        ordineRepository.saveAndFlush(ordine);

        saveOrdiniPizzaComponent.saveOrdiniPizza(ordine, nuovePizze);
        return ordineMapper.ordineToDTO(ordine);
    }


    @Override
    public Double calcoloTotale(String codiceOrdine) {
        Ordine ordine = ordineRepository.findById(codiceOrdine)
                .orElseThrow(() -> new NotFoundException("Order not found."));

        // Calculate the total of the pizzas
        double totalPizze = ordine.getPizzeOrdinate().stream()
                .mapToDouble(lp -> lp.getPizza().getPrezzo() * lp.getQuantita())
                .sum();

        // Add surcharge if it is Priority
        if (ordine instanceof OrdinePrioritario) {
            totalPizze += ((OrdinePrioritario) ordine).getSovrapprezzo();
        }

        return totalPizze;
    }

    @Override
    public OrdineDTO getOrdineById(String codice) {
        Ordine ordine = ordineRepository.findById(codice)
                .orElseThrow(() -> new NotFoundException("Order not found."));

        return ordineMapper.ordineToDTO(ordine);
    }

    @Override
    public Collection<OrdineDTO> selectAll() {
        return ordineRepository.findAll()
                .stream()
                .map(ordine -> {
                    if (ordine instanceof OrdinePrioritario) {
                        return ordinePrioritarioMapper.ordineprioritarioToDTO((OrdinePrioritario) ordine);
                    }
                    return ordineMapper.ordineToDTO(ordine);
                })
                .toList();
    }

    @Override
    public Map<String, Integer> getDettaglioPizze(String codiceOrdine) {
        Ordine ordine = ordineRepository.findById(codiceOrdine)
                .orElseThrow(() -> new NotFoundException("Order not found."));

        return ordine.getPizzeOrdinate().stream()
                .collect(Collectors.toMap(
                        link -> link.getPizza().getNome(),
                        OrdinePizza::getQuantita
                ));
    }

    @Override
    public void deleteOrdine(String id) {
        if (!ordineRepository.existsById(id)) {
            throw new NotFoundException("Order not found.");
        }
        ordineRepository.deleteById(id);
    }
}


