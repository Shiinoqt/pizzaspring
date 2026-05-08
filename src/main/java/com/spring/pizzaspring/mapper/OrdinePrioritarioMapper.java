package com.spring.pizzaspring.mapper;

import com.spring.pizzaspring.dto.OrdinePrioritarioDTO;
import com.spring.pizzaspring.model.OrdinePrioritario;
import org.mapstruct.Mapper;

@Mapper
public interface OrdinePrioritarioMapper {
    OrdinePrioritarioDTO ordineprioritarioToDTO(OrdinePrioritario ordinePrioritario);
    OrdinePrioritario DTOToOrdineprioritario(OrdinePrioritarioDTO ordinePrioritarioDTO);
}
