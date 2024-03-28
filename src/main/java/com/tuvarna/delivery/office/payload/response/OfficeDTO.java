package com.tuvarna.delivery.office.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record OfficeDTO(

   @Schema(example = "1")
   Long id,
   @Schema(example = "1")
   Long cityId,
   @Schema(example = "Sofia")
   String cityName,
   @Schema(example = "Office 1")
   String name,
   @Schema(example = "+359888123456")
   String phoneNumber,
   @Schema(example = "33")
   Integer numberOfEmployees
) {}
