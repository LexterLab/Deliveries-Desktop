package com.tuvarna.delivery.authentication.controller;

import com.tuvarna.delivery.authentication.model.constant.AuthenticationType;
import com.tuvarna.delivery.authentication.payload.request.LoginRequestDTO;
import com.tuvarna.delivery.authentication.payload.request.SignUpRequestDTO;
import com.tuvarna.delivery.authentication.service.AuthenticationService;
import com.tuvarna.delivery.jwt.JWTAuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@Tag(name = "Authentication REST APIs for Authentication Resource")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @Operation(
            summary = "Login User REST API",
            description = "Login User REST API is used to get user's bearer token"
    )
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Http Status 200 SUCCESS"),
            @ApiResponse( responseCode = "400", description = "Http Status 400 BAD REQUEST")
    })
    @PostMapping("signin")
    public ResponseEntity<JWTAuthenticationResponse> login(@Valid @RequestBody LoginRequestDTO loginDTO) {
        return ResponseEntity.ok(authenticationService.login(loginDTO));
    }

    @Operation(
            summary = "Sign Up REST API",
            description = "Sign Up REST API  is used to create a new user"
    )
    @ApiResponses( value = {
            @ApiResponse( responseCode = "201", description = "Http Status 201 CREATED"),
            @ApiResponse( responseCode = "400", description = "Http Status 400 BAD REQUEST")
    })
    @PostMapping("signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignUpRequestDTO requestDTO,
                                         @RequestParam AuthenticationType type) {
        String response = authenticationService.signUp(requestDTO, type);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
