package com.spring.pizzaspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrdinePrioritarioDTO extends OrdineDTO {
    private double sovraprezzo;
    private String tipoOrdine = "prioritario";
}