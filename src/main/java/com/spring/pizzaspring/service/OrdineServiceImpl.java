package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.dto.OrdinePrioritarioDTO;
import com.spring.pizzaspring.mapper.OrdineMapper;
import com.spring.pizzaspring.model.Ordine;
import com.spring.pizzaspring.repository.ClienteRepository;
import com.spring.pizzaspring.repository.OrdineRepository;
import com.spring.pizzaspring.repository.RiderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OrdineServiceImpl implements OrdineService{

    @Autowired
    private OrdineRepository ordineRepository;

    @Autowired
    private OrdineMapper ordineMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RiderRepository riderRepository;

    @Override
    public void creaOrdine(OrdineDTO dto) {
    }

    @Override
    public void creaOrdinePrioritario(OrdinePrioritarioDTO dto) {
    }

    @Override
    public void assegnaRider(String codiceOrdine, String idRider) {
    }

    @Override
    public Double calcoloTotale(String codiceOrdine) {
        return 0.0;
    }

    @Override
    public OrdineDTO getOrdineById(Long id) {
        Ordine ordine = ordineRepository.findById(id)
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
    public void deleteOrdine(Long id) {
        if (!ordineRepository.existsById(id)) {
            throw new RuntimeException("Ordine non trovato");
        }
        ordineRepository.deleteById(id);
    }
}
