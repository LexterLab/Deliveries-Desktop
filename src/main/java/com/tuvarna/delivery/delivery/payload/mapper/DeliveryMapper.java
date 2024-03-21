package com.tuvarna.delivery.delivery.payload.mapper;

import com.tuvarna.delivery.delivery.model.Delivery;
import com.tuvarna.delivery.delivery.payload.DeliveryRequestDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DeliveryMapper {
    DeliveryMapper INSTANCE = Mappers.getMapper(DeliveryMapper.class);

    Delivery dtoToEntity(DeliveryRequestDTO requestDTO);
}
