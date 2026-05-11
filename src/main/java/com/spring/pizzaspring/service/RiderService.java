package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.RiderDTO;

import java.util.Collection;

public interface RiderService {
    RiderDTO registraRider(RiderDTO riderDTO);
    RiderDTO getRiderById(Long id);
    RiderDTO updateRider(Long id, RiderDTO newRiderDTO);
    Collection<RiderDTO> selectAll();
    void deleteRider(Long id);
}
