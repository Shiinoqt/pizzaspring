package com.spring.pizzaspring.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PizzaDTO {
    private Long idPizza;
    private String nome;
    private String descrizione;
    private double prezzo;
}