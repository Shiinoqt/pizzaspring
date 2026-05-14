package com.spring.pizzaspring.mapper;

import com.spring.pizzaspring.dto.OrdinePrioritarioDTO;
import com.spring.pizzaspring.model.OrdinePrioritario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrdinePizzaMapper.class})
public interface OrdinePrioritarioMapper {
    @Mapping(target = "tipoOrdine", constant = "prioritario")
    @Mapping(source = "cliente.idCliente", target = "idCliente")
    @Mapping(source = "rider.idRider", target = "idRider")
    OrdinePrioritarioDTO ordineprioritarioToDTO(OrdinePrioritario ordinePrioritario);
    
    @Mapping(source = "idCliente", target = "cliente", ignore = true)
    @Mapping(source = "idRider", target = "rider", ignore = true)
    @Mapping(target = "sovrapprezzo", ignore = true)
    OrdinePrioritario DTOToOrdineprioritario(OrdinePrioritarioDTO ordinePrioritarioDTO);
}
