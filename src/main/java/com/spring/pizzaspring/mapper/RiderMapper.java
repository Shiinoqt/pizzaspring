package com.spring.pizzaspring.mapper;

import com.spring.pizzaspring.dto.RiderDTO;
import com.spring.pizzaspring.model.Ordine;
import com.spring.pizzaspring.model.Rider;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RiderMapper {

    @Mapping(target = "codiciOrdini", source = "ordini")
    RiderDTO riderToDTO(Rider rider);

    default List<String> mapOrdiniToCodici(Collection<Ordine> ordini) {
        if (ordini == null) {
            return Collections.emptyList();
        }
        return ordini.stream()
                .map(Ordine::getCodice)
                .toList();
    }

    @Mapping(target = "ordini", ignore = true)
    Rider DTOToRider(RiderDTO riderDTO);
}
