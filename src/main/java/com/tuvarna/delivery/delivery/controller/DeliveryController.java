package com.tuvarna.delivery.delivery.controller;

import com.tuvarna.delivery.delivery.model.constant.StatusType;
import com.tuvarna.delivery.delivery.payload.request.DeliveryRequestDTO;
import com.tuvarna.delivery.delivery.payload.request.UpdateDeliveryStatusRequestDTO;
import com.tuvarna.delivery.delivery.payload.response.DeliveryDTO;
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
            @Schema(example = "1") @RequestParam(value = "statusId", required = false) Long statusId,
            @Schema(example = "user") @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
            return ResponseEntity.ok(deliveryService.retrieveAndFilterDeliveries(username, statusId, pageNo, pageSize, sortBy, sortDir));
    }


    @GetMapping("{deliveryId}")
    @PreAuthorize("hasAnyRole('COURIER', 'ADMIN')")
    @Operation(
            summary = "Get Delivery REST API",
            description = "Get Delivery REST API is used to retrieve a delivery"
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
    public ResponseEntity<DeliveryDTO> retrieveDeliveryInfo(@PathVariable @Schema(example = "1") Long deliveryId) {
        return ResponseEntity.ok(deliveryService.retrieveDeliveryInfo(deliveryId));
    }

    @DeleteMapping("{deliveryId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete Delivery REST API",
            description = "Delete Delivery REST API is used to delete delivery by identifier"
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
    public ResponseEntity<Void> deleteRecipeById(@PathVariable @Schema(example = "1") Long deliveryId) {
        deliveryService.deleteDelivery(deliveryId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{deliveryId}")
    @PreAuthorize("hasAnyRole('COURIER', 'ADMIN')")
    @Operation(
            summary = "Update Delivery REST API",
            description = "Update Delivery REST API is used to update delivery by identifier"
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
    public ResponseEntity<DeliveryDTO> updateRecipeById(@PathVariable @Schema(example = "1") Long deliveryId,
                                                        @RequestBody @Valid DeliveryRequestDTO requestDTO) {
        return ResponseEntity.ok(deliveryService.updateDelivery(deliveryId, requestDTO));
    }
    @PutMapping("{deliveryId}/statuses/{statusId}")
    @PreAuthorize("hasAnyRole('COURIER', 'ADMIN')")
    @Operation(
            summary = "Update Delivery Status REST API",
            description = "Update Delivery Status REST API is used to update delivery status by identifier"
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
    public ResponseEntity<DeliveryDTO> updateRecipeStatus(@PathVariable @Schema(example = "1") Long deliveryId,
                                                          @PathVariable @Schema(example = "1") Long statusId) {
        return ResponseEntity.ok(deliveryService.updateDeliveryStatus(deliveryId, statusId));
    }
}
