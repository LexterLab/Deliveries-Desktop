package com.tuvarna.delivery.delivery.payload.mapper;

import com.tuvarna.delivery.delivery.model.Delivery;
import com.tuvarna.delivery.delivery.payload.request.DeliveryRequestDTO;
import com.tuvarna.delivery.delivery.payload.response.DeliveryDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DeliveryMapper {
    DeliveryMapper INSTANCE = Mappers.getMapper(DeliveryMapper.class);

    @Mapping(expression = "java(delivery.getStatus().getType())", target = "statusName")
    @Mapping(expression = "java(delivery.getToCity().getName())", target = "toCityName")
    @Mapping(expression = "java(delivery.getFromCity().getName())", target = "fromCityName")
    @Mapping(expression = "java(delivery.getCourier() != null && delivery.getCourier().getUser() != null ? delivery.getCourier().getUser().getUsername() : \"none\")", target = "courierName")
    DeliveryDTO entityToDTO(Delivery delivery);

    Delivery dtoToEntity(DeliveryRequestDTO requestDTO);

    List<DeliveryDTO> entityToDTO(Iterable<Delivery> deliveries);
}
