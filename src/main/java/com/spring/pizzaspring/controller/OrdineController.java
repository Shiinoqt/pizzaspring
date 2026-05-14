package com.spring.pizzaspring.controller;

import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.dto.OrdinePizzaDTO;
import com.spring.pizzaspring.dto.OrdinePrioritarioDTO;
import com.spring.pizzaspring.service.OrdineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordini")
public class OrdineController {

    @Autowired
    private OrdineService service;

    @PostMapping(path = "/", consumes = "application/json")
    public ResponseEntity<OrdineDTO> carica(@RequestBody @Valid OrdineDTO ordineDTO) {
        OrdineDTO dto = service.creaOrdine(ordineDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping(path = "/prioritari", consumes = "application/json")
    public ResponseEntity<OrdineDTO> caricaPrio(@RequestBody @Valid OrdinePrioritarioDTO ordinePrioritarioDTO) {
        OrdineDTO dto = service.creaOrdinePrioritario(ordinePrioritarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{codiceOrdine}")
    public ResponseEntity<OrdineDTO> modificaOrdine(@PathVariable String codiceOrdine, @RequestBody @Valid OrdineDTO newOrdine) {
        OrdineDTO dto = service.modificaOrdine(codiceOrdine, newOrdine);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/{codiceOrdine}")
    public ResponseEntity<OrdineDTO> patchPizze(@PathVariable String codiceOrdine, @RequestBody List<OrdinePizzaDTO> nuovePizze) {
        OrdineDTO dto = service.patchPizzeOnly(codiceOrdine, nuovePizze);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping(path = "/{codiceOrdine}/assegna-rider/{idRider}")
    public ResponseEntity<OrdineDTO> assegnaRider(@PathVariable String codiceOrdine, @PathVariable Long idRider) {
        OrdineDTO dto = service.assegnaRider(codiceOrdine, idRider);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(path = "/{codiceOrdine}/totale", produces = "application/json")
    public ResponseEntity<Double> totale(@PathVariable String codiceOrdine) {
        Double response = service.calcoloTotale(codiceOrdine);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{codiceOrdine}/dettaglio", produces = "application/json")
    public ResponseEntity<Map<String, Integer>> getDettaglio(@PathVariable String codiceOrdine) {
        Map<String, Integer> response = service.getDettaglioPizze(codiceOrdine);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{codiceOrdine}", produces = "application/json")
    public ResponseEntity<OrdineDTO> getOrdineById(@PathVariable String codiceOrdine) {
        OrdineDTO dto = service.getOrdineById(codiceOrdine);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Collection<OrdineDTO>> getAll() {
        Collection<OrdineDTO> response = service.selectAll();
        return ResponseEntity.ok(response);    }

    @DeleteMapping(path = "/{codiceOrdine}")
    public ResponseEntity<Void> eliminaPerId(@PathVariable String codiceOrdine) {
        service.deleteOrdine(codiceOrdine);
        return ResponseEntity.noContent().build();
    }
}
