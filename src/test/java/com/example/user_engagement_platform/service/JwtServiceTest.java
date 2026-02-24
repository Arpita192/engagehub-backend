package com.example.user_engagement_platform.service;

import com.example.user_engagement_platform.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setup() {
        jwtService = new JwtService();
    }

    @Test
    void shouldGenerateToken() {
        UserEntity user = new UserEntity();
        user.setEmail("test@test.com");

        String token = jwtService.generateToken(user);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void shouldExtractEmailFromToken() {
        UserEntity user = new UserEntity();
        user.setEmail("arpita@test.com");

        String token = jwtService.generateToken(user);

        String extractedEmail = jwtService.extractEmail(token);

        assertEquals("arpita@test.com", extractedEmail);
    }

    @Test
    void shouldReturnTrueForValidToken() {
        UserEntity user = new UserEntity();
        user.setEmail("valid@test.com");

        String token = jwtService.generateToken(user);

        boolean isValid = jwtService.isTokenValid(token);

        assertTrue(isValid);
    }

    @Test
    void shouldReturnFalseForInvalidToken() {
        String invalidToken = "invalid.jwt.token";

        boolean isValid = jwtService.isTokenValid(invalidToken);

        assertFalse(isValid);
    }

}