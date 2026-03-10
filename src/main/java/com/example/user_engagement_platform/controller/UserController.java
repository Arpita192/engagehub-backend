package com.example.user_engagement_platform.controller;

import com.example.user_engagement_platform.dto.*;
import com.example.user_engagement_platform.service.DailerService;
import com.example.user_engagement_platform.service.NotificationService;
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
    private final DailerService dailerService;
    private final NotificationService notificationService;

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
    @PostMapping("/dialer")
    public ResponseEntity<ApiResponse<String>> createDialer(
            @RequestBody DialerDtoRequest request){

        String message = dailerService.saveDialerData(request);

        return ResponseEntity.ok(
                ApiResponse.success(message, null)
        );
    }

    @PostMapping("notification")
    public ResponseEntity<ApiResponse<String>> createNotification(@Valid @RequestBody NotificationDtoRequest request){

        String message = notificationService.saveNotificationData(request);

        return ResponseEntity.ok(
                ApiResponse.success(message,null)
        );
    }
}