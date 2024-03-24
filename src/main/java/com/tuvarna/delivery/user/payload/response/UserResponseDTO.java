package com.tuvarna.delivery.user.payload.response;

import java.util.List;

public record UserResponseDTO(
        int pageNo,
        int pageSize,
        Long totalElements,
        int totalPages,
        boolean last,
        List<UserDTO> users
) {
}
