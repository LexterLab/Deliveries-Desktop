package com.tuvarna.delivery.office.controller;

import com.tuvarna.delivery.office.payload.request.CourierRequestDTO;
import com.tuvarna.delivery.office.payload.request.UpdateCourierRequestDTO;
import com.tuvarna.delivery.office.payload.response.CourierDTO;
import com.tuvarna.delivery.office.payload.response.CourierResponseDTO;
import com.tuvarna.delivery.office.payload.response.OfficeDTO;
import com.tuvarna.delivery.office.service.OfficeService;
import com.tuvarna.delivery.utils.AppConstants;
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

import java.util.List;

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

    @Operation(
            summary = "Delete Courier REST API",
            description = "Delete Courier REST API is used to delete a courier account"
    )
    @ApiResponses( value = {
            @ApiResponse( responseCode = "204", description = "Http Status 204 NO CONTENT"),
            @ApiResponse( responseCode = "401", description = "Http Status 401 UNAUTHORIZED"),
            @ApiResponse( responseCode = "403", description = "Http Status 403 FORBIDDEN"),
            @ApiResponse( responseCode = "404", description = "Http Status 404 NOT FOUND")
    })
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{officeId}/couriers/{courierId}")
    public ResponseEntity<Void> deleteCourier(@Schema(example = "1") @PathVariable Long officeId,
                                              @Schema(example = "1") @PathVariable Long courierId) {
        officeService.deleteCourier(officeId, courierId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Update Courier REST API",
            description = "Update Courier REST API is used to update a courier account"
    )
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Http Status 200 SUCCESS"),
            @ApiResponse( responseCode = "401", description = "Http Status 401 UNAUTHORIZED"),
            @ApiResponse( responseCode = "403", description = "Http Status 403 FORBIDDEN"),
            @ApiResponse( responseCode = "404", description = "Http Status 404 NOT FOUND")
    })
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{officeId}/couriers/{courierId}")
    public ResponseEntity<UpdateCourierRequestDTO> updateCourierData(@Schema(example = "1") @PathVariable Long officeId,
                                                                     @Schema(example = "1") @PathVariable Long courierId,
                                                                     @RequestBody @Valid UpdateCourierRequestDTO requestDTO) {
        return ResponseEntity.ok(officeService.updateCourier(officeId, courierId, requestDTO));
    }

    @Operation(
            summary = "Get Courier REST API",
            description = "Get Courier REST API is used to retrieve courier's info"
    )
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Http Status 200 SUCCESS"),
            @ApiResponse( responseCode = "401", description = "Http Status 401 UNAUTHORIZED"),
            @ApiResponse( responseCode = "403", description = "Http Status 403 FORBIDDEN"),
            @ApiResponse( responseCode = "404", description = "Http Status 404 NOT FOUND")
    })
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('COURIER')")
    @GetMapping("{officeId}/couriers/{courierId}")
    public ResponseEntity<CourierDTO> retrieveCourierInfo(@Schema(example = "1") @PathVariable Long officeId,
                                                          @Schema(example = "1") @PathVariable Long courierId) {
        return ResponseEntity.ok(officeService.retrieveCourierInfo(officeId, courierId));
    }

    @Operation(
            summary = "Get All Couriers from Office REST API",
            description = "Get All Couriers from Office REST API is used to retrieve all couriers from an office"
    )
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Http Status 200 SUCCESS"),
            @ApiResponse( responseCode = "401", description = "Http Status 401 UNAUTHORIZED"),
            @ApiResponse( responseCode = "403", description = "Http Status 403 FORBIDDEN"),
            @ApiResponse( responseCode = "404", description = "Http Status 404 NOT FOUND")
    })
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{officeId}/couriers")
    public ResponseEntity<CourierResponseDTO> getAllCouriersFromOffice(
             @Schema(example = "1") @PathVariable Long officeId,
             @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
             @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
             @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
             @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return ResponseEntity.ok(officeService.getAllCouriersFromOffice(officeId, pageNo, pageSize, sortBy, sortDir));
    }

    @Operation(
            summary = "Get All Offices REST API",
            description = "Get All Offices  REST API is used to retrieve all offices"
    )
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Http Status 200 SUCCESS"),
            @ApiResponse( responseCode = "401", description = "Http Status 401 UNAUTHORIZED"),
            @ApiResponse( responseCode = "403", description = "Http Status 403 FORBIDDEN")
    })
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<OfficeDTO>> getAllOffices() {
        return ResponseEntity.ok(officeService.getAllOffices());
    }
}
