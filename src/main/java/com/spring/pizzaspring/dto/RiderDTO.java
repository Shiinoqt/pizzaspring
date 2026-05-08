package com.spring.pizzaspring.dto;

import com.spring.pizzaspring.model.Ordine;
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
    private Collection<Ordine> ordini;
}
