package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.dto.OrdinePrioritarioDTO;
import com.spring.pizzaspring.mapper.OrdineMapper;
import com.spring.pizzaspring.mapper.OrdinePrioritarioMapper;
import com.spring.pizzaspring.model.Ordine;
import com.spring.pizzaspring.model.OrdinePrioritario;
import com.spring.pizzaspring.model.Rider;
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
    private OrdinePrioritarioMapper ordinePrioritarioMapper;

    @Autowired
    private RiderRepository riderRepository;

    @Override
    public void creaOrdine(OrdineDTO dto) {
        Ordine ordine = ordineMapper.DTOToOrdine(dto);
        ordineRepository.save(ordine);
    }

    @Override
    public void creaOrdinePrioritario(OrdinePrioritarioDTO dto) {
        OrdinePrioritario ordinePrioritario = ordinePrioritarioMapper.DTOToOrdineprioritario(dto);
        ordineRepository.save(ordinePrioritario);
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
    public Double calcoloTotale(String codiceOrdine) {
        return 0.0;
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
    public void deleteOrdine(String id) {
        if (!ordineRepository.existsById(id)) {
            throw new RuntimeException("Ordine non trovato");
        }
        ordineRepository.deleteById(id);
    }
}
