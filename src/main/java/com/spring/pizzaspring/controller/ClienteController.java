package com.spring.pizzaspring.controller;

import com.spring.pizzaspring.dto.ClienteDTO;
import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/clienti")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ClienteDTO registra(@RequestBody @Valid ClienteDTO clienteDTO) {
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

    @GetMapping(produces = "application/json")
    public Collection<ClienteDTO> getClienti() {
        return service.selectAll();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ClienteDTO getClienteById(@PathVariable Long id) {
        return service.getClienteById(id);
    }

    @DeleteMapping(path = "/{id}")
    public void eliminaPerId(@PathVariable Long id) {
        service.deleteCliente(id);
    }
}
