package com.tuvarna.delivery.delivery.payload.mapper;

import com.tuvarna.delivery.delivery.model.Status;
import com.tuvarna.delivery.delivery.payload.response.StatusDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface StatusMapper {
    StatusMapper INSTANCE = Mappers.getMapper(StatusMapper.class);

    StatusDTO entityToDTO(Status status);

    List<StatusDTO> entityToDTO(Iterable<Status> statuses);
}
