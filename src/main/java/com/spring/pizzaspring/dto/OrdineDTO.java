package com.spring.pizzaspring.dto;

import com.spring.pizzaspring.model.Cliente;
import com.spring.pizzaspring.model.OrdinePizza;
import com.spring.pizzaspring.model.Rider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdineDTO {
    private String codice;
    private Cliente cliente;
    private Collection<OrdinePizza> pizzeOrdinate;
    private Rider rider;
}
