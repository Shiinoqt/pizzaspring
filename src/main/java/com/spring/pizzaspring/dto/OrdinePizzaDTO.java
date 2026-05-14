package com.spring.pizzaspring.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdinePizzaDTO {

    @JsonIgnore
    @Null(groups = ClienteDTO.OnCreate.class)
    @NotNull(groups = ClienteDTO.OnUpdate.class)
    @Positive(groups = ClienteDTO.OnUpdate.class)
    private Long id;

    @NotNull
    private Long idPizza;

    @NotNull
    @Min(value = 1, message = "La quantità deve essere almeno 1")
    private Integer quantita;
}