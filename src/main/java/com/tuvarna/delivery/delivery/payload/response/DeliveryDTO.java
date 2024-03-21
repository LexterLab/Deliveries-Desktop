package com.tuvarna.delivery.delivery.payload.response;

public record DeliveryDTO(
    String username,
    String courierName,
    String statusName,
    String fromCityName,
    String toCityName,
    Double weightKG,
    String productName,
    Double totalPrice,
    String details
) {
}
