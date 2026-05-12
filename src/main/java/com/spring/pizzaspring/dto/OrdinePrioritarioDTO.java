package com.spring.pizzaspring.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrdinePrioritarioDTO extends OrdineDTO {
    @NotNull(message = "Ordine prioritario richiede il sovrapprezzo")
    private double sovrapprezzo;
    private String tipoOrdine = "prioritario";
}