package com.spring.pizzaspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdineDTO {
    private String codice;
    private Long idCliente;
    private Collection<OrdinePizzaDTO> pizzeOrdinate;
    private Long idRider;
}
