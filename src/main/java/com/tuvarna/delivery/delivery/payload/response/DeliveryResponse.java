package com.tuvarna.delivery.delivery.payload.response;

import java.util.List;

public record DeliveryResponse(
         int pageNo,
         int pageSize,
         Long totalElements,
         int totalPages,
         boolean last,
         List<DeliveryDTO>deliveries
) {
}
