package com.tuvarna.delivery.office.payload.response;

import java.util.List;

public record CourierResponseDTO(
        int pageNo,
        int pageSize,
        Long totalElements,
        int totalPages,
        boolean last,
        List<CourierDTO> couriers
) {
}
