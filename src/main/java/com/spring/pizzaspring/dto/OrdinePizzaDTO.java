package com.spring.pizzaspring.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdinePizzaDTO {
    private Long id;
    private Long idPizza;
    private Integer quantita;
}