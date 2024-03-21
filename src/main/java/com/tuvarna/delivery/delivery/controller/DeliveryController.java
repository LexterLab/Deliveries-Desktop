package com.tuvarna.delivery.delivery.controller;

import com.tuvarna.delivery.delivery.payload.DeliveryRequestDTO;
import com.tuvarna.delivery.delivery.service.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @Operation(
            summary = "Request Delivery REST API",
            description = "Request Delivery REST API is used to request delivery"
    )
    @ApiResponses( value = {
            @ApiResponse( responseCode = "201", description = "Http Status 200 CREATED"),
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
}
