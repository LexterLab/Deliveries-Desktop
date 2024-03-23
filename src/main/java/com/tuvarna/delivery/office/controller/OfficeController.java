package com.tuvarna.delivery.office.controller;

import com.tuvarna.delivery.office.payload.request.CourierRequestDTO;
import com.tuvarna.delivery.office.service.OfficeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/offices")
@Tag(name = "Office REST APIs")
public class OfficeController {
    private final OfficeService officeService;

    @Operation(
            summary = "Enlist Courier REST API",
            description = "Enlist Courier REST API is used to add a courier to office"
    )
    @ApiResponses( value = {
            @ApiResponse( responseCode = "201", description = "Http Status 201 CREATED"),
            @ApiResponse( responseCode = "401", description = "Http Status 401 UNAUTHORIZED"),
            @ApiResponse( responseCode = "403", description = "Http Status 403 FORBIDDEN"),
            @ApiResponse( responseCode = "404", description = "Http Status 404 NOT FOUND")
    })
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("{officeId}/couriers")
    public ResponseEntity<CourierRequestDTO> addCourierToOffice(@Schema(example = "1") @PathVariable Long officeId,
                                                                @RequestBody @Valid CourierRequestDTO requestDTO) {
        return new ResponseEntity<>(officeService.enlistCourierToOffice(officeId, requestDTO), HttpStatus.CREATED);
    }
}
