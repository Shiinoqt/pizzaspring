package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.RiderDTO;
import com.spring.pizzaspring.mapper.ClienteMapper;
import com.spring.pizzaspring.mapper.RiderMapper;
import com.spring.pizzaspring.model.Ordine;
import com.spring.pizzaspring.model.Rider;
import com.spring.pizzaspring.repository.ClienteRepository;
import com.spring.pizzaspring.repository.OrdineRepository;
import com.spring.pizzaspring.repository.RiderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class RiderServiceImpl implements RiderService{

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private OrdineRepository ordineRepository;

    @Autowired
    private RiderMapper riderMapper;

    @Override
    @Transactional
    public RiderDTO registraRider(RiderDTO riderDTO) {
        Rider rider = riderMapper.DTOToRider(riderDTO);
        Rider savedRider = riderRepository.save(rider);
        return riderMapper.riderToDTO(savedRider);
    }

    @Override
    @Transactional
    public RiderDTO assegnaRiderOrdine(Long idRider, String codice) {
        Rider rider = riderRepository.findById(idRider) // Fetch del rider
                .orElseThrow(() -> new RuntimeException("Rider non trovato"));

        Ordine ordine = ordineRepository.findById(codice) // Fetch dell'ordine
                .orElseThrow(() -> new RuntimeException("Ordine non trovato"));

        // Set rider to Order
        ordine.setRider(rider);

        ordineRepository.save(ordine);
        return riderMapper.riderToDTO(rider);
    }

    @Override
    public RiderDTO selectById(Long id) {
        Rider rider = riderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rider non trovato"));
        return riderMapper.riderToDTO(rider);
    }

    @Override
    public Collection<RiderDTO> selectAll() {
        return riderRepository.findAll()
                .stream()
                .map(riderMapper::riderToDTO)
                .toList();
    }

    @Override
    public void deleteRider(Long id) {
        if (!riderRepository.existsById(id)) {
            throw new RuntimeException("Rider non trovato");
        }
        riderRepository.deleteById(id);
    }
}
