package com.spring.pizzaspring.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrdinePrioritarioDTO extends OrdineDTO {
//    @NotNull
//    @DecimalMin(value = "0.01")
//    private Double sovrapprezzo;
    private String tipoOrdine = "prioritario";
}