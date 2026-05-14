package com.spring.pizzaspring.service;

import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.dto.OrdinePizzaDTO;
import com.spring.pizzaspring.dto.OrdinePrioritarioDTO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface OrdineService {

    OrdineDTO creaOrdine(OrdineDTO dto);
    OrdineDTO creaOrdinePrioritario(OrdinePrioritarioDTO dto);
    OrdineDTO assegnaRider(String codiceOrdine, Long idRider);
    OrdineDTO modificaOrdine(String codiceOrdine, OrdineDTO newOrdineDTO);
    OrdineDTO patchPizzeOnly(String codiceOrdine, List<OrdinePizzaDTO> nuovePizze);
    Double calcoloTotale(String codiceOrdine);
    OrdineDTO getOrdineById(String id);
    Collection<OrdineDTO> selectAll();
    Map<String, Integer> getDettaglioPizze(String codiceOrdine);
    void deleteOrdine(String id);
}
