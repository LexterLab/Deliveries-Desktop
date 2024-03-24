package com.tuvarna.delivery.user.payload.mapper;

import com.tuvarna.delivery.user.model.User;
import com.tuvarna.delivery.user.payload.request.UserRequestDTO;
import com.tuvarna.delivery.user.payload.response.UserDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO entityToDTO(User user);

    void updateUserWithDTO(UserRequestDTO requestDTO, @MappingTarget User user);

    List<UserDTO> entityToDTO(Iterable<User> users);
}
