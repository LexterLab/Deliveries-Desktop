package com.tuvarna.delivery.user.payload.response;

public record UserDTO(
        Long id,
        String fistName,
        String lastName,
        String phoneNumber,
        String address
) {
}
