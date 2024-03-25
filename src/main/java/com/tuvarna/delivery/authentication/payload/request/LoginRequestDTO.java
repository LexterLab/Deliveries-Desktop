package com.tuvarna.delivery.authentication.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public record LoginRequestDTO(
        @Schema(example = "user")
        @NotEmpty(message = "Please enter your username")
        String username,

        @NotEmpty(message = "Please enter your password")
        @Schema(example = "!user123")
        String password
) { }
