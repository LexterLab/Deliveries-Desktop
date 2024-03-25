package com.tuvarna.delivery.delivery.payload.request;

import com.tuvarna.delivery.delivery.model.constant.StatusType;

public record UpdateDeliveryStatusRequestDTO(
        StatusType type
) {
}
