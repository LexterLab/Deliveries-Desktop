package com.tuvarna.delivery.office.payload.response;

public record CourierDTO(
        Long id,
        String workPhoneNumber,
        Integer yearsOfExperience,
        Long userId,
        Long officeId,
        String officeName,
        String firstName,
        String lastName
) {
}
