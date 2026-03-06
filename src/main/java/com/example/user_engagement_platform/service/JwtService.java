package com.example.user_engagement_platform.service;

import com.example.user_engagement_platform.entity.UserEntity;

public interface JwtService {

    String generateToken(UserEntity user);

    String generateRefreshToken(UserEntity user);

    String extractEmail(String token);

    boolean isTokenValid(String token);
}