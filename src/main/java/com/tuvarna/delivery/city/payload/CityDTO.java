package com.tuvarna.delivery.city.payload;

import io.swagger.v3.oas.annotations.media.Schema;

public record CityDTO(
        @Schema(example = "Varna")
        String name
) {
}
