package com.tuvarna.delivery.office.payload.mapper;

import com.tuvarna.delivery.office.model.Courier;
import com.tuvarna.delivery.office.payload.request.CourierRequestDTO;
import com.tuvarna.delivery.office.payload.request.UpdateCourierRequestDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CourierMapper {
    CourierMapper INSTANCE = Mappers.getMapper(CourierMapper.class);
    @Mapping(expression = "java(courier.getOffice().getId())", target = "officeId")
    UpdateCourierRequestDTO entityToDTO(Courier courier);

    Courier dtoToEntity(CourierRequestDTO requestDTO);
    void updateEntityWithDTO(UpdateCourierRequestDTO requestDTO, @MappingTarget  Courier courier);
}
