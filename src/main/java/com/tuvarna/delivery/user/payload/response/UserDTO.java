package com.tuvarna.delivery.user.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserDTO(
        @Schema(example = "1")
        Long id,
        @Schema(example = "User")
        String firstName,
        @Schema(example = "User")
        String lastName,
        @Schema(example = "+359********")
        String phoneNumber,
        @Schema(example = "User Boulevard")
        String address
) {
}
