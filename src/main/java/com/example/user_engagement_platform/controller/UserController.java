package com.example.user_engagement_platform.controller;

import com.example.user_engagement_platform.dto.*;
import com.example.user_engagement_platform.enums.Constant;
import com.example.user_engagement_platform.service.UserService;
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

    @PostMapping("/auth/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> createUser(
            @Valid @RequestBody RegisterRequest request) {

        RegisterResponse response = userService.createUser(request);

        ApiResponse<RegisterResponse> apiResponse =
                new ApiResponse<>("success", "User registered successfully", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }


    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<LoginResponse>> checkUser(@Valid @RequestBody LoginRequest request){
        LoginResponse response = userService.login(request);

        ApiResponse<LoginResponse> apiResponse =
                new ApiResponse<>("success", "Login successful", response);

        return ResponseEntity.ok(apiResponse);
    }
    @PatchMapping("/consents")
    public ResponseEntity<ApiResponse<ConsentResponse>> updateDetail(@Valid @RequestBody ConsentRequest request){
        ConsentResponse response = userService.updateConsent(request);

        ApiResponse<ConsentResponse> apiResponse = new ApiResponse<>("success","Updated successful",response);
        return ResponseEntity.ok(apiResponse);
    }
}
