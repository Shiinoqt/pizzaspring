package com.spring.pizzaspring.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiderDTO {
    private Long idRider;
    @NotNull
    private String nome;
    private Collection<String> codiciOrdini;
}
