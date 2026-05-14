package com.spring.pizzaspring.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiderDTO {
    @Null(groups = ClienteDTO.OnCreate.class)
    @NotNull(groups = ClienteDTO.OnUpdate.class)
    @Positive(groups = ClienteDTO.OnUpdate.class)
    private Long idRider;

    @NotBlank
    @Size(min = 2, max = 50)
    private String nome;

    private Collection<String> codiciOrdini;
}
