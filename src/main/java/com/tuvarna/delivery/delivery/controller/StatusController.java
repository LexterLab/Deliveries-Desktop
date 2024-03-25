package com.tuvarna.delivery.delivery.controller;

import com.tuvarna.delivery.delivery.payload.response.StatusDTO;
import com.tuvarna.delivery.delivery.service.StatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/statuses")
@RequiredArgsConstructor
@Tag(name = "Status REST APIs")
public class StatusController {
    private final StatusService service;


    @GetMapping("")
    @PreAuthorize("hasAnyRole('COURIER', 'ADMIN')")
    @Operation(
            summary = "Get statuses REST API",
            description = "Get statuses REST API is used to retrieve all statuses"
    )
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Http Status 200 SUCCESS"),
            @ApiResponse( responseCode = "401", description = "Http Status 401 UNAUTHORIZED"),
            @ApiResponse( responseCode = "403", description = "Http Status 403 FORBIDDEN")
    })
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    public ResponseEntity<List<StatusDTO>> getAllStatuses() {
        return ResponseEntity.ok(service.retrieveStatusList());
    }
}
