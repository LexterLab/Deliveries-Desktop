package com.tuvarna.delivery.user.controller;

import com.tuvarna.delivery.delivery.payload.response.DeliveryResponse;
import com.tuvarna.delivery.user.payload.request.UserRequestDTO;
import com.tuvarna.delivery.user.payload.response.UserDTO;
import com.tuvarna.delivery.user.payload.response.UserResponseDTO;
import com.tuvarna.delivery.user.service.UserService;
import com.tuvarna.delivery.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@Tag(name = "User REST APIs")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Filter User deliveries REST API",
            description = "Filter User deliveries REST API is used to retrieve and filter user deliveries"
    )
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Http Status 200 SUCCESS"),
            @ApiResponse( responseCode = "401", description = "Http Status 401 UNAUTHORIZED"),
            @ApiResponse( responseCode = "403", description = "Http Status 403 FORBIDDEN"),
            @ApiResponse( responseCode = "404", description = "Http Status 403 FORBIDDEN")
    })
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @GetMapping("deliveries")
    public ResponseEntity<DeliveryResponse> getAllDeliveriesByUser
            (
                    Authentication authentication,
                    @RequestParam(required = false) LocalDate afterDate,
                    @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                    @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                    @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
            ) {
        return ResponseEntity.ok(userService.retrieveUserDeliveries(authentication.getName(), afterDate, pageNo,
                pageSize, sortBy, sortDir));
    }
    @Operation(
            summary = "Get All Users REST API",
            description = "Get All Users REST API is used to retrieve all users"
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
    public ResponseEntity<UserResponseDTO> getAllUsers
            (
                    @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                    @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                    @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
            )
    {
        return ResponseEntity.ok(userService.retrieveAllUsers(pageNo, pageSize, sortBy, sortDir));
    }

    @Operation(
            summary = "Get User Info REST API",
            description = "Get User Info REST API is used to retrieve authenticated user info"
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
    @PreAuthorize("hasRole('USER')")
    @GetMapping("info")
    public ResponseEntity<UserDTO> getUserInfo(Authentication authentication) {
        return ResponseEntity.ok(userService.retrieveUserInfo(authentication.getName()));
    }



    @Operation(
            summary = "Get User REST API",
            description = "Get User REST API is used to retrieve user by identifier"
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
    @GetMapping("{userId}")
    public ResponseEntity<UserDTO> getUserById(@Schema(example = "2") @PathVariable Long userId) {
        return ResponseEntity.ok(userService.retrieveUserInfo(userId));
    }

    @Operation(
            summary = "Delete User REST API",
            description = "Delete User REST API is used to delete user by identifier"
    )
    @ApiResponses( value = {
            @ApiResponse( responseCode = "204", description = "Http Status 200 SUCCESS"),
            @ApiResponse( responseCode = "401", description = "Http Status 401 UNAUTHORIZED"),
            @ApiResponse( responseCode = "403", description = "Http Status 403 FORBIDDEN"),
            @ApiResponse( responseCode = "404", description = "Http Status 404 NOT FOUND")
    })
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUserById(@Schema(example = "2") @PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Update User REST API",
            description = "Update User REST API is used to update user by identifier"
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
    @PutMapping("{userId}")
    public ResponseEntity<UserDTO> updateUserById(@Schema(example = "2") @PathVariable Long userId,
                                                  @RequestBody @Valid UserRequestDTO requestDTO) {
        return ResponseEntity.ok(userService.updateUserInfo(userId, requestDTO));
    }
}
