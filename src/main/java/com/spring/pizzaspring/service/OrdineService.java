package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.dto.OrdinePrioritarioDTO;
import com.spring.pizzaspring.model.Ordine;

import java.util.Collection;

public interface OrdineService {

    void creaOrdine(OrdineDTO dto);
    void creaOrdinePrioritario(OrdinePrioritarioDTO dto);
    void assegnaRider(String codiceOrdine, String idRider);
    Double calcoloTotale(String codiceOrdine);
    OrdineDTO getOrdineById(Long id);
    Collection<OrdineDTO> selectAll();
    void deleteOrdine(Long id);
}
