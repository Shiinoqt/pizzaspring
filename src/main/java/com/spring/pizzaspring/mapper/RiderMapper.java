package com.spring.pizzaspring.mapper;

import com.spring.pizzaspring.dto.RiderDTO;
import com.spring.pizzaspring.model.Rider;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RiderMapper {
    RiderDTO riderToDTO(Rider rider);
    Rider DTOToRider(RiderDTO riderDTO);
}
