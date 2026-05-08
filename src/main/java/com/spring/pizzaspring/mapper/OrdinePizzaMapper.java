package com.spring.pizzaspring.mapper;

import com.spring.pizzaspring.dto.OrdinePizzaDTO;
import com.spring.pizzaspring.model.OrdinePizza;
import org.mapstruct.Mapper;

@Mapper
public interface OrdinePizzaMapper {
    OrdinePizzaDTO ordinepizzaToDTO(OrdinePizza ordinePizza);
    OrdinePizza DTOTOordinepizza(OrdinePizzaDTO ordinePizzaDTO);
}
