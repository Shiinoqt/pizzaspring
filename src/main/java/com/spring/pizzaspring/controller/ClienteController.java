package com.spring.pizzaspring.controller;

import com.spring.pizzaspring.dto.ClienteDTO;
import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/clienti")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ClienteDTO> registra(@RequestBody @Valid ClienteDTO clienteDTO) {
        ClienteDTO dto = service.registraCliente(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ClienteDTO> aggiorna(@PathVariable Long id, @RequestBody @Valid ClienteDTO clienteDTO) {
        ClienteDTO dto = service.updateCliente(id, clienteDTO);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(path = "/{id}/ordini", produces = "application/json")
    public ResponseEntity<Collection<OrdineDTO>> getOrdini(@PathVariable Long id) {
        Collection<OrdineDTO> response = service.getOrdiniByCliente(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Collection<ClienteDTO>> getClienti() {
        Collection<ClienteDTO> response = service.selectAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Long id) {
        ClienteDTO dto = service.getClienteById(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> eliminaPerId(@PathVariable Long id) {
        service.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}
