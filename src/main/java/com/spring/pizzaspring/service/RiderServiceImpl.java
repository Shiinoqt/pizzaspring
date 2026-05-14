package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.dto.RiderDTO;
import com.spring.pizzaspring.exceptions.NotFoundException;
import com.spring.pizzaspring.mapper.OrdineMapper;
import com.spring.pizzaspring.mapper.RiderMapper;
import com.spring.pizzaspring.model.Rider;
import com.spring.pizzaspring.repository.RiderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RiderServiceImpl implements RiderService{
    @Autowired
    private OrdineMapper ordineMapper;

    @Autowired
    private RiderRepository riderRepository;

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
    public RiderDTO getRiderById(Long id) {
        Rider rider = riderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rider not found."));
        return riderMapper.riderToDTO(rider);
    }

    @Override
    public RiderDTO updateRider(Long id, RiderDTO newRiderDTO) {
        Rider rider = riderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rider not found."));

        rider.setNome(newRiderDTO.getNome());

        Rider savedRider = riderRepository.save(rider);

        return riderMapper.riderToDTO(savedRider);
    }

    @Override
    public Collection<OrdineDTO> getOrdiniByRider(Long idRider) {
        Rider rider = riderRepository.findById(idRider)
                .orElseThrow(() -> new NotFoundException("Rider not found."));

        return rider.getOrdini().stream()
                .map(ordineMapper::ordineToDTO)
                .toList();
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
            throw new NotFoundException("Rider not found.");
        }
        riderRepository.deleteById(id);
    }
}
