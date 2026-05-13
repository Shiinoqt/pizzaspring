package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.dto.OrdinePizzaDTO;
import com.spring.pizzaspring.dto.OrdinePrioritarioDTO;
import com.spring.pizzaspring.mapper.OrdineMapper;
import com.spring.pizzaspring.mapper.OrdinePizzaMapper;
import com.spring.pizzaspring.mapper.OrdinePrioritarioMapper;
import com.spring.pizzaspring.model.*;
import com.spring.pizzaspring.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    private Cliente validateOrdine(OrdineDTO dto) {
        // Check id cliente nell'ordine
        if (dto.getIdCliente() == null) {
            throw new IllegalArgumentException("Ordine senza cliente");
        }

        // Check esistenza del cliente nel db
        Cliente cliente = clienteRepository.findById(dto.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente not found."));

        // Check presenza pizze
        if (dto.getPizzeOrdinate() == null || dto.getPizzeOrdinate().isEmpty()) {
            throw new IllegalArgumentException("Ordine senza pizze");
        }

        return cliente;
    }

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
    public void creaOrdine(OrdineDTO dto) {
        Cliente cliente = validateOrdine(dto);

        Ordine ordine = ordineMapper.DTOToOrdine(dto);
        ordine.setCliente(cliente);
        Ordine savedOrdine = ordineRepository.save(ordine);

        saveOrdiniPizza(savedOrdine, dto.getPizzeOrdinate());
    }

    @Override
    @Transactional
    public void creaOrdinePrioritario(OrdinePrioritarioDTO dto) {
        Cliente cliente = validateOrdine(dto);
        if (dto.getSovrapprezzo() <= 0) {
            throw new IllegalArgumentException("Sovrapprezzo deve essere maggiore di 0");
        }

        OrdinePrioritario ordinePrioritario = ordinePrioritarioMapper.DTOToOrdineprioritario(dto);
        ordinePrioritario.setCliente(cliente);
        OrdinePrioritario savedOrdinePrio = ordineRepository.save(ordinePrioritario);

        saveOrdiniPizza(savedOrdinePrio, dto.getPizzeOrdinate());
    }

    @Override
    public void assegnaRider(String codiceOrdine, Long idRider) {
        Ordine ordine = ordineRepository.findById(codiceOrdine)
                .orElseThrow(() -> new RuntimeException("Ordine non trovato"));
        Rider rider = riderRepository.findById(idRider)
                .orElseThrow(() -> new RuntimeException("Rider non trovato"));
        ordine.setRider(rider);
        ordineRepository.save(ordine);
    }

    @Override
    @Transactional
    public void modificaOrdine(String codiceOrdine, OrdineDTO newOrdineDTO) {
        Ordine ordine = ordineRepository.findById(codiceOrdine)
                .orElseThrow(() -> new RuntimeException("Ordine non trovato"));

        validateOrdine(newOrdineDTO);

        if (newOrdineDTO.getIdCliente() != null) {
            Cliente cliente = clienteRepository.findById(newOrdineDTO.getIdCliente())
                    .orElseThrow(() -> new RuntimeException("Cliente non trovato"));
            ordine.setCliente(cliente);
        }

        ordine.getPizzeOrdinate().clear();
        ordineRepository.saveAndFlush(ordine); // Force the delete of old pizzas

        // Save the new pizza list
        saveOrdiniPizza(ordine, newOrdineDTO.getPizzeOrdinate());
    }
    @Override
    @Transactional
    public void patchPizzeOnly(String codiceOrdine, List<OrdinePizzaDTO> nuovePizze) {
        Ordine ordine = ordineRepository.findById(codiceOrdine)
                .orElseThrow(() -> new RuntimeException("Ordine non trovato"));

        if (nuovePizze == null || nuovePizze.isEmpty()) {
            throw new IllegalArgumentException("L'ordine deve contenere almeno una pizza");
        }

        ordine.getPizzeOrdinate().clear();
        ordineRepository.saveAndFlush(ordine);

        saveOrdiniPizza(ordine, nuovePizze);
    }


    @Override
    public Double calcoloTotale(String codiceOrdine) {
        Ordine ordine = ordineRepository.findById(codiceOrdine)
                .orElseThrow(() -> new RuntimeException("Ordine non trovato"));

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
                .orElseThrow(() -> new RuntimeException("Ordine non trovato"));

        return ordineMapper.ordineToDTO(ordine);
    }

    @Override
    public Collection<OrdineDTO> selectAll() {
        return ordineRepository.findAll()
                .stream()
                .map(ordineMapper::ordineToDTO)
                .toList();
    }

    @Override
    public Map<String, Integer> getDettaglioPizze(String codiceOrdine) {
        Ordine ordine = ordineRepository.findById(codiceOrdine)
                .orElseThrow(() -> new RuntimeException("Ordine non trovato"));

        return ordine.getPizzeOrdinate().stream()
                .collect(Collectors.toMap(
                        link -> link.getPizza().getNome(),
                        OrdinePizza::getQuantita
                ));
    }

    @Override
    public void deleteOrdine(String id) {
        if (!ordineRepository.existsById(id)) {
            throw new RuntimeException("Ordine non trovato");
        }
        ordineRepository.deleteById(id);
    }
}


