package com.tuvarna.delivery.user.payload.response;

import com.tuvarna.delivery.user.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

public record UserDTO(
        @Schema(example = "1")
        Long id,
        @Schema(example = "user")
        String username,
        @Schema(example = "User")
        String firstName,
        @Schema(example = "User")
        String lastName,
        @Schema(example = "+359********")
        String phoneNumber,
        @Schema(example = "User Boulevard")
        String address,
        Set<Role> roles
) {
}
