package com.spring.pizzaspring.controller;

import com.spring.pizzaspring.dto.ClienteDTO;
import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/clienti")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ClienteDTO registra(@RequestBody ClienteDTO clienteDTO) {
        return service.registraCliente(clienteDTO);
    }

    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ClienteDTO aggiorna(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        return service.updateCliente(id, clienteDTO);
    }

    @GetMapping(path = "/{id}/ordini", produces = "application/json")
    public Collection<OrdineDTO> getOrdini(@PathVariable Long id) {
        return service.getOrdiniByCliente(id);
    }
}
