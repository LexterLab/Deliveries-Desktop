package com.tuvarna.delivery.office.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

public record UpdateCourierRequestDTO(
        @Schema(example = "James")
        @NotEmpty(message = "Please enter a first name.")
        @Length(min = 1, max = 50, message = "First name must not exceed 50 characters.")
        @Pattern(regexp = "^[A-Za-z'-. ]{1,50}$", message = "Please enter a valid first name.")
        String firstName,

        @Schema(example = "Bond")
        @NotEmpty(message = "Please enter a last name.")
        @Length(min = 1, max = 50, message = "Last name must not exceed 50 characters.")
        @Pattern(regexp = "^[A-Za-z'-. ]{1,50}$", message = "Please enter a valid last name.")
        String lastName,
        @Schema(example = "+359888123456")
        @NotEmpty(message = "Please enter a phone number.")
        @Pattern(regexp ="^\\+?[1-9][0-9]{7,14}$", message = "Please enter a valid phone number.")
        @Length(min = 7, max = 14, message = "Phone number must be 7-14 characters long.")
        String workPhoneNumber,
        @Schema(example = "2")
        @Min(value = 0, message = "Courier can't have negative experience")
        @Max(value = 70, message = "Please input reasonable experience" )
        Integer yearsOfExperience,
        @Schema(example = "1")
        Long officeId
)  {
}
