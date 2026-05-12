package com.spring.pizzaspring.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Long idCliente;
    @NotNull
    private String nome, indirizzo, telefono;
    @NotEmpty
    private Collection<OrdineDTO> ordini;
}
