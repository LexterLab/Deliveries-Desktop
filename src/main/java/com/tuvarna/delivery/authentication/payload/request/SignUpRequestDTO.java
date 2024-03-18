package com.tuvarna.delivery.authentication.payload.request;

import com.tuvarna.delivery.authentication.payload.validator.PasswordValueMatches;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

@PasswordValueMatches.List({
        @PasswordValueMatches(
                field = "password",
                fieldMatch = "matchingPassword"
        )
})
public record SignUpRequestDTO (
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
        @Schema(example = "user")
        @NotEmpty(message = "Please enter a username.")
        @Size(min = 4, message = "Username must be at least 8 characters long.")
        @Size(max = 100, message = "Username must not exceed 100 characters.")
        String username,

        @Schema(example = "!User123")
        @NotEmpty(message = "Please enter a password.")
        @Size(min = 8, message = "Password must be at least 8 characters long.")
        @Size(max = 100, message = "Password must not exceed 100 characters.")
        String password,

        @Schema(example = "!User123")
        @NotEmpty(message = "Please enter a matching password.")
        @Size(min = 8, message = "Matching Password should be at least 8 characters long.")
        @Size(max = 100, message = "Matching Password must not exceed 100 characters.")
        String matchingPassword,
        @Schema(example = "+359888123456")
        @NotEmpty(message = "Please enter a phone number.")
        @Pattern(regexp ="^\\+?[1-9][0-9]{7,14}$", message = "Please enter a valid phone number.")
        @Length(min = 7, max = 14, message = "Phone number must be 7-14 characters long.")
        String phoneNumber,
        @Schema(example = "123 Main Street LA, CA 12345")
        @NotEmpty(message = "Please enter an address.")
        @Length(min = 8, max = 150, message = "Address must be 8-150 characters long")
        String address
){}
