package com.spring.pizzaspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdineDTO {
    private String codice;
    private Long idCliente;
    private List<OrdinePizzaDTO> pizzeOrdinate;
    private Long idRider;
}
