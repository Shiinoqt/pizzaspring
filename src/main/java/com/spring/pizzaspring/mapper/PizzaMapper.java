package com.spring.pizzaspring.mapper;

import com.spring.pizzaspring.dto.PizzaDTO;
import com.spring.pizzaspring.model.Pizza;
import org.mapstruct.Mapper;

@Mapper
public interface PizzaMapper {
    PizzaDTO pizzaToDTO(Pizza pizza);

    Pizza DTOToPizza(PizzaDTO pizzaDTO);
}
