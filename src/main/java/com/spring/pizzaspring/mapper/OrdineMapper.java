package com.spring.pizzaspring.mapper;

import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.model.Ordine;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrdineMapper {
    OrdineDTO ordineToDTO(Ordine ordine);
    Ordine DTOToOrdine(OrdineDTO ordineDTO);
}
