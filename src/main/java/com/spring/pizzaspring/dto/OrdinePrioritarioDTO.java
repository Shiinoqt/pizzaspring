package com.spring.pizzaspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdinePrioritarioDTO{
    private double sovraprezzo;
    private String tipoOrdine = "prioritario";
}