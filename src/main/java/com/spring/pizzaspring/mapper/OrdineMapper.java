package com.spring.pizzaspring.mapper;

import com.spring.pizzaspring.dto.OrdineDTO;
import com.spring.pizzaspring.model.Ordine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrdinePizzaMapper.class})
public interface OrdineMapper {
    @Mapping(source = "cliente.idCliente", target = "idCliente")
    @Mapping(source = "rider.idRider", target = "idRider")
    @Mapping(target = "pizzeOrdinate", source = "pizzeOrdinate")
    OrdineDTO ordineToDTO(Ordine ordine);
    
    @Mapping(source = "idCliente", target = "cliente", ignore = true)
    @Mapping(source = "idRider", target = "rider", ignore = true)
    @Mapping(target = "pizzeOrdinate", source = "pizzeOrdinate")
    Ordine DTOToOrdine(OrdineDTO ordineDTO);
}
