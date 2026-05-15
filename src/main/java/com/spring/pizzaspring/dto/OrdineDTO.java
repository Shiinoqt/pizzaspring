package com.spring.pizzaspring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class OrdineDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Auto-generated, do not include in requests")
    private String codice;

    private Long idCliente;

    @NotEmpty
    @Valid
    private List<OrdinePizzaDTO> pizzeOrdinate;

    private Long idRider;
}
