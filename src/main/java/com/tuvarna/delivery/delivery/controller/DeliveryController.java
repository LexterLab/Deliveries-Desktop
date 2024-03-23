package com.tuvarna.delivery.delivery.controller;

import com.tuvarna.delivery.delivery.model.constant.StatusType;
import com.tuvarna.delivery.delivery.payload.request.DeliveryRequestDTO;
import com.tuvarna.delivery.delivery.payload.response.DeliveryResponse;
import com.tuvarna.delivery.delivery.service.DeliveryService;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Delivery REST APIs")
@RequestMapping("api/v1/deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @Operation(
            summary = "Request Delivery REST API",
            description = "Request Delivery REST API is used to request delivery"
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
    @PreAuthorize("hasRole('USER')")
    @PostMapping("")

    public ResponseEntity<DeliveryRequestDTO> requestDelivery(@RequestBody @Valid DeliveryRequestDTO requestDTO,
                                                              Authentication authentication) {
        return new ResponseEntity<>(deliveryService.requestDelivery(requestDTO,
                authentication.getName()), HttpStatus.CREATED);
    }


    @GetMapping("")
    @PreAuthorize("hasAnyRole('COURIER', 'ADMIN')")
    @Operation(
            summary = "Filter deliveries REST API",
            description = "Filter deliveries REST API is used to retrieve and filter deliveries"
    )
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Http Status 200 SUCCESS"),
            @ApiResponse( responseCode = "401", description = "Http Status 401 UNAUTHORIZED"),
            @ApiResponse( responseCode = "403", description = "Http Status 403 FORBIDDEN")
    })
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    public ResponseEntity<DeliveryResponse> getAllDeliveriesFiltered(
            @RequestParam(value = "status", required = false) StatusType type,
            @Schema(example = "user") @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
            return ResponseEntity.ok(deliveryService.retrieveAndFilterDeliveries(username, type.getName(), pageNo, pageSize, sortBy, sortDir));
    }
}
