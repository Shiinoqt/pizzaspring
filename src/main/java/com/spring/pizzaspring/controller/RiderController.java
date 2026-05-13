package com.spring.pizzaspring.controller;

import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.dto.RiderDTO;
import com.spring.pizzaspring.service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/riders")
public class RiderController {

    @Autowired
    private RiderService service;

    @PostMapping(path = "/", consumes = "application/json")
    public RiderDTO carica(@RequestBody RiderDTO riderDTO) {
        return service.registraRider(riderDTO);
    }

    @GetMapping(produces = "application/json")
    public Collection<RiderDTO> getAll() {
        return service.selectAll();
    }

    @PutMapping(path = "/{id}", produces = "application/json", consumes = "application/json")
    public RiderDTO aggiorna(@PathVariable Long id, @RequestBody RiderDTO riderDTO) {
        return service.updateRider(id, riderDTO);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public RiderDTO cercaPerId(@PathVariable Long id) {
        return service.getRiderById(id);
    }

    @DeleteMapping(path = "/{id}")
    public void eliminaPerId(@PathVariable Long id) {
        service.deleteRider(id);
    }

    @GetMapping(path = "/{id}/ordini", produces = "application/json")
    public Collection<OrdineDTO> getOrdiniRider(@PathVariable Long id) {
        return service.getOrdiniByRider(id);
    }



}
