package com.spring.pizzaspring.controller;

import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.dto.OrdinePrioritarioDTO;
import com.spring.pizzaspring.service.OrdineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/ordini")
public class OrdineController {

    @Autowired
    private OrdineService service;

    @PostMapping(path = "/", consumes = "application/json")
    public void carica(@RequestBody @Valid OrdineDTO ordineDTO) {
        service.creaOrdine(ordineDTO);
    }

    @PostMapping(path = "/prioritari", consumes = "application/json")
    public void caricaPrio(@RequestBody @Valid OrdinePrioritarioDTO ordinePrioritarioDTO) {
        service.creaOrdinePrioritario(ordinePrioritarioDTO);
    }

    @PatchMapping(path = "/{codiceOrdine}/assegna-rider/{idRider}")
    public void assegnaRider(@PathVariable String codiceOrdine, @PathVariable Long idRider) {
        service.assegnaRider(codiceOrdine, idRider);
    }

    @GetMapping(path = "/{codiceOrdine}/totale", produces = "application/json")
    public Double totale(@PathVariable String codiceOrdine) {
        return service.calcoloTotale(codiceOrdine);
    }

    @GetMapping(path = "/{codiceOrdine}/dettaglio", produces = "application/json")
    public Map<String, Integer> getDettaglio(@PathVariable String codiceOrdine) {
        return service.getDettaglioPizze(codiceOrdine);
    }

    @GetMapping(path = "/{codiceOrdine}", produces = "application/json")
    public OrdineDTO getOrdineById(@PathVariable String codiceOrdine) {
        return service.getOrdineById(codiceOrdine);
    }

    @GetMapping(produces = "application/json")
    public Collection<OrdineDTO> getAll() {
        return service.selectAll();
    }

    @DeleteMapping(path = "/{codiceOrdine}")
    public void eliminaPerId(@PathVariable String codiceOrdine) {
        service.deleteOrdine(codiceOrdine);
    }
}
