package com.example.user_engagement_platform.service;

import com.example.user_engagement_platform.dto.*;

public interface UserService {

    RegisterResponse createUser(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    RefreshTokenResponse refreshAccessToken(RefreshTokenRequest request);
}