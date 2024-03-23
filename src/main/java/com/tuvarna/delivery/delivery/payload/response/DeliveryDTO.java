package com.tuvarna.delivery.delivery.payload.response;

import java.time.LocalDateTime;

public record DeliveryDTO(
        Long id,
        String username,
        String courierName,
        String statusName,
        String fromCityName,
        String toCityName,
        Double weightKG,
        String productName,
        Double totalPrice,
        String details,
        LocalDateTime orderedAt,
        LocalDateTime deliveredAt
) {
}
