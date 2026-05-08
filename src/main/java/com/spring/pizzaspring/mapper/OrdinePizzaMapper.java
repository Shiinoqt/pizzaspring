package com.spring.pizzaspring.mapper;

import com.spring.pizzaspring.dto.OrdinePizzaDTO;
import com.spring.pizzaspring.model.OrdinePizza;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrdinePizzaMapper {
    @Mapping(source = "pizza.idPizza", target = "idPizza")
    @Mapping(source = "quantità", target = "quantita")
    OrdinePizzaDTO ordinepizzaToDTO(OrdinePizza ordinePizza);
    
    @Mapping(source = "idPizza", target = "pizza.idPizza")
    @Mapping(source = "quantita", target = "quantità")
    @Mapping(target = "ordine", ignore = true)
    OrdinePizza DTOTOordinepizza(OrdinePizzaDTO ordinePizzaDTO);
}
