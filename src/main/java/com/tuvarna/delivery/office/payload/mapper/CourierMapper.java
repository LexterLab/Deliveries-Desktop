package com.tuvarna.delivery.office.payload.mapper;

import com.tuvarna.delivery.office.model.Courier;
import com.tuvarna.delivery.office.payload.request.CourierRequestDTO;
import com.tuvarna.delivery.office.payload.request.UpdateCourierRequestDTO;
import com.tuvarna.delivery.office.payload.response.CourierDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CourierMapper {
    CourierMapper INSTANCE = Mappers.getMapper(CourierMapper.class);
    @Mapping(expression = "java(courier.getOffice().getId())", target = "officeId")
    UpdateCourierRequestDTO entityToDTO(Courier courier);

    @Mapping(expression = "java(courier.getOffice().getId())", target = "officeId")
    @Mapping(expression = "java(courier.getUser().getId())", target = "userId")
    @Mapping(expression = "java(courier.getUser().getFirstName())", target = "firstName")
    @Mapping(expression = "java(courier.getUser().getLastName())", target = "lastName")
    CourierDTO entityToResponse(Courier courier);

    List<CourierDTO> entityToDTO(Iterable<Courier> couriers);

    Courier dtoToEntity(CourierRequestDTO requestDTO);
    void updateEntityWithDTO(UpdateCourierRequestDTO requestDTO, @MappingTarget  Courier courier);
}
