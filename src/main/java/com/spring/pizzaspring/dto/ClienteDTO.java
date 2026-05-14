package com.spring.pizzaspring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    public interface OnCreate {}
    public interface OnUpdate {}

    @NotNull(groups = OnUpdate.class)
    @Positive(groups = OnUpdate.class)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Auto-generated, do not include in requests")
    private Long idCliente;

    @NotBlank
    @Size(min = 2, max = 50)
    private String nome;

    @NotBlank
    @Size(max = 255)
    private String indirizzo;

    @NotBlank
    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$")
    private String telefono;

    @NotEmpty
    @Valid
    private Collection<OrdineDTO> ordini;
}
