package com.tuvarna.delivery.office.payload.mapper;

import com.tuvarna.delivery.office.model.Courier;
import com.tuvarna.delivery.office.payload.request.CourierRequestDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CourierMapper {
    CourierMapper INSTANCE = Mappers.getMapper(CourierMapper.class);

    Courier dtoToEntity(CourierRequestDTO requestDTO);
}
