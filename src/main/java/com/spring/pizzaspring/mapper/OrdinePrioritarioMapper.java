package com.spring.pizzaspring.mapper;

import com.spring.pizzaspring.dto.OrdinePrioritarioDTO;
import com.spring.pizzaspring.model.OrdinePrioritario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrdinePrioritarioMapper {
    @Mapping(target = "tipoOrdine", constant = "prioritario")
    OrdinePrioritarioDTO ordineprioritarioToDTO(OrdinePrioritario ordinePrioritario);
    
    OrdinePrioritario DTOToOrdineprioritario(OrdinePrioritarioDTO ordinePrioritarioDTO);
}
