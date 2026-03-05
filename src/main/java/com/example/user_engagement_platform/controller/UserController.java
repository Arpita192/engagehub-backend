package com.example.user_engagement_platform.controller;

import com.example.user_engagement_platform.dto.*;
import com.example.user_engagement_platform.service.UserService;
import com.example.user_engagement_platform.service.ConsentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ConsentService consentService;

    @PostMapping("/auth/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> createUser(
            @Valid @RequestBody RegisterRequest request) {

        RegisterResponse response = userService.createUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully", response));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<LoginResponse>> checkUser(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = userService.login(request);

        return ResponseEntity.ok(
                ApiResponse.success("Login successful", response)
        );
    }

    @PatchMapping("/consents")
    public ResponseEntity<ApiResponse<ConsentResponse>> updateDetail(
            @Valid @RequestBody ConsentRequest request) {

        ConsentResponse response = consentService.updateConsent(request);

        return ResponseEntity.ok(
                ApiResponse.success("Consent updated successfully", response)
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<RefreshTokenResponse>> refreshToken(
            @RequestBody RefreshTokenRequest request) {
        RefreshTokenResponse response =
                userService.refreshAccessToken(request);

        return ResponseEntity.ok(
                ApiResponse.success("Token refreshed", response)
        );
    }
}