package com.tuvarna.delivery.office.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

public record CourierRequestDTO(
        @Schema(example = "+359888123456")
        @NotEmpty(message = "Please enter a phone number.")
        @Pattern(regexp ="^\\+?[1-9][0-9]{7,14}$", message = "Please enter a valid phone number.")
        @Length(min = 7, max = 14, message = "Phone number must be 7-14 characters long.")
        String workPhoneNumber,
        @Schema(example = "2")
        @Min(value = 0, message = "Courier can't have negative experience")
        @Max(value = 70, message = "Please input reasonable experience" )
        Integer yearsOfExperience,
        @Schema(example = "user")
        @NotEmpty(message = "Please enter a username.")
        @Size(min = 4, message = "Username must be at least 8 characters long.")
        @Size(max = 100, message = "Username must not exceed 100 characters.")
        String username
)  {
}
