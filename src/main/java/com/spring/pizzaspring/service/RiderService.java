package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.RiderDTO;

import java.util.Collection;

public interface RiderService {
    RiderDTO registraRider(RiderDTO riderDTO);
    RiderDTO assegnaRiderOrdine(Long idRider, String codice);
    Collection<RiderDTO> selectAll();
    void deleteRider(RiderDTO riderDTO);
}
