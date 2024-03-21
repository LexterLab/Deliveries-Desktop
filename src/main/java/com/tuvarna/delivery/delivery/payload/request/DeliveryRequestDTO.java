package com.tuvarna.delivery.delivery.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public record DeliveryRequestDTO(

       @NotEmpty(message = "Please input from City")
       @Schema(example = "Sofia")
       @Length(min = 2, message = "City should be at least 2 characters long")
       String fromCityName,
       @NotEmpty(message = "Please input to City")
       @Schema(example = "Varna")
       @Length(min = 2, message = "City should be at least 2 characters long")
       String toCityName,

       @Schema(example = "1")
       @Min(value = 1, message = "We are currently accepting min of 1KG weight")
       @Max(value = 100, message = "We are currently accepting max of 100KG weight")
       Double weightKG,


       @Schema(example = "50.00")
       @Min(value = 0, message = "Total price shouldn't be below 0")
       Double totalPrice,

       @Schema(example = "Optional info")
       String details,
       @Schema(example = "Laptop")
       @NotEmpty(message = "Please input product name ")
       @Length(min = 1, max = 100, message = "Product name must be between 1 and 100 characters long")
       String productName


) {
}
