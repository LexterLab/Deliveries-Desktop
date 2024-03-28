package com.tuvarna.delivery.office.payload.mapper;

import com.tuvarna.delivery.office.model.Office;
import com.tuvarna.delivery.office.payload.response.OfficeDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface OfficeMapper {
    OfficeMapper INSTANCE = Mappers.getMapper(OfficeMapper.class);
    @Mapping(expression = "java(office.getCity().getId())", target = "cityId")
    @Mapping(expression = "java(office.getCity().getName())", target = "cityName")
    @Mapping(expression = "java(office.getCouriers().size())", target = "numberOfEmployees")
    OfficeDTO entityToDTO(Office office);

    List<OfficeDTO> entityToDTO(Iterable<Office> offices);
}
