package com.spring.pizzaspring.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdineDTO {
    private String codice;

    @NotNull(message = "ID Cliente richiesto")
    private Long idCliente;

    @NotEmpty(message = "L'Ordine deve contenere almeno una pizza")
    private List<OrdinePizzaDTO> pizzeOrdinate;

    private Long idRider;
}
