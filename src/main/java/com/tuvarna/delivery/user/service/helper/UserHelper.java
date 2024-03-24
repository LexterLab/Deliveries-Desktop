package com.tuvarna.delivery.user.service.helper;

import com.tuvarna.delivery.user.model.User;
import com.tuvarna.delivery.user.payload.mapper.UserMapper;
import com.tuvarna.delivery.user.payload.response.UserDTO;
import com.tuvarna.delivery.user.payload.response.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserHelper {
    public UserResponseDTO getUserResponse(Page<User> userPage) {
        List<UserDTO> users = UserMapper.INSTANCE.entityToDTO(userPage.getContent());
        return new UserResponseDTO(
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.isLast(),
                users
        );
    }
}
