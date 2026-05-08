package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.dto.OrdinePrioritarioDTO;

import java.util.Collection;

public interface OrdineService {

    void creaOrdine(OrdineDTO dto);
    void creaOrdinePrioritario(OrdinePrioritarioDTO dto);
    void assegnaRider(String codiceOrdine, Long idRider);
    Double calcoloTotale(String codiceOrdine);
    OrdineDTO getOrdineById(String id);
    Collection<OrdineDTO> selectAll();
    void deleteOrdine(String id);
}
