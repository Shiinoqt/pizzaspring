package com.spring.pizzaspring.controller;

import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.dto.RiderDTO;
import com.spring.pizzaspring.service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/riders")
public class RiderController {

    @Autowired
    private RiderService service;

    @PostMapping(path = "/", consumes = "application/json")
    public ResponseEntity<RiderDTO> carica(@RequestBody RiderDTO riderDTO) {
        RiderDTO dto = service.registraRider(riderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Collection<RiderDTO>> getAll() {
        Collection<RiderDTO> response = service.selectAll();
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<RiderDTO> aggiorna(@PathVariable Long id, @RequestBody RiderDTO riderDTO) {
        RiderDTO dto = service.updateRider(id, riderDTO);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<RiderDTO> cercaPerId(@PathVariable Long id) {
        RiderDTO dto = service.getRiderById(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> eliminaPerId(@PathVariable Long id) {
        service.deleteRider(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{id}/ordini", produces = "application/json")
    public ResponseEntity<Collection<OrdineDTO>> getOrdiniRider(@PathVariable Long id) {
        Collection<OrdineDTO> response = service.getOrdiniByRider(id);
        return ResponseEntity.ok(response);
    }
}
