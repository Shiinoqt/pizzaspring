package com.spring.pizzaspring.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdinePizzaDTO {
    private Long id;

    @NotNull(message = "ID Pizza richiesto")
    private Long idPizza;

    @NotNull(message = "La quantità è richiesta")
    @Min(value = 1, message = "La quantità deve essere almeno 1")
    private Integer quantita;
}