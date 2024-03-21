package com.tuvarna.delivery.city.payload.mapper;

import com.tuvarna.delivery.city.model.City;
import com.tuvarna.delivery.city.payload.CityDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CityMapper {
    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    CityDTO entityToDto(City city);

    List<CityDTO> entityToDto(Iterable<City> cities);
}
