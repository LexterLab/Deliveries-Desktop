package com.tuvarna.delivery.delivery.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record DeliveryDTO(
        @Schema(example = "1")
        Long id,
        @Schema(example = "user")
        String username,
        @Schema(example = "courier")
        String courierName,
        @Schema(example = "WAITING FOR COURIER")
        String statusName,
        @Schema(example = "Varna")
        String fromCityName,
        @Schema(example = "Sofia")
        String toCityName,
        @Schema(example = "20.00")
        Double weightKG,
        @Schema(example = "Skittles")
        String productName,
        @Schema(example = "20.00")
        Double totalPrice,
        @Schema(example = "Deliver with care!")
        String details,
        LocalDateTime orderedAt,
        LocalDateTime deliveredAt
) {
}
