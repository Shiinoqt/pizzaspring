package com.spring.pizzaspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiderDTO {
    private Long idRider;
    private String nome;
    private Collection<String> codiciOrdini;
}
