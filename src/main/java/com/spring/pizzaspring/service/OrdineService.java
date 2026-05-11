package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.dto.OrdinePrioritarioDTO;

import java.util.Collection;
import java.util.Map;

public interface OrdineService {

    void creaOrdine(OrdineDTO dto);
    void creaOrdinePrioritario(OrdinePrioritarioDTO dto);
    void assegnaRider(String codiceOrdine, Long idRider);
    Double calcoloTotale(String codiceOrdine);
    OrdineDTO getOrdineById(String id);
    Collection<OrdineDTO> selectAll();
    Map<String, Integer> getDettaglioPizze(String codiceOrdine);
    void deleteOrdine(String id);
}
