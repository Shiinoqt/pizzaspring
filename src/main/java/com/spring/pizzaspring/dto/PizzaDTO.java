package com.spring.pizzaspring.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PizzaDTO {
    @Null(groups = ClienteDTO.OnCreate.class)
    @NotNull(groups = ClienteDTO.OnUpdate.class)
    @Positive(groups = ClienteDTO.OnUpdate.class)
    private Long idPizza;

    @NotBlank
    @Size(min = 2, max = 50)
    private String nome;

    @NotBlank
    @Size(max = 500)
    private String descrizione;

    @NotNull
    @DecimalMin(value = "0")
    private Double prezzo;
}