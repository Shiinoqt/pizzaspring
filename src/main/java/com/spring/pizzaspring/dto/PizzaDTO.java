package com.spring.pizzaspring.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PizzaDTO {
    private Long idPizza;
    @NotNull
    private String nome;
    @NotNull
    private String descrizione;
    @NotNull
    private double prezzo;
}