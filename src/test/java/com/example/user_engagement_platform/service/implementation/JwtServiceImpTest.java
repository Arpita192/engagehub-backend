package com.example.user_engagement_platform.service.implementation;

import com.example.user_engagement_platform.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JwtServiceImpTest {

    private JwtServiceImp jwtService;

    @BeforeEach
    void setup() {
        String secret = "myverystrongsecretkeymyverystrongsecretkey";
        jwtService = new JwtServiceImp(secret);
    }

    @Test
    void shouldGenerateAccessToken() {

        UserEntity user = new UserEntity();
        user.setEmail("test@test.com");

        String token = jwtService.generateToken(user);

        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void shouldGenerateRefreshToken() {

        UserEntity user = new UserEntity();
        user.setEmail("test@test.com");

        String token = jwtService.generateRefreshToken(user);

        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void shouldExtractEmailFromToken() {

        UserEntity user = new UserEntity();
        user.setEmail("test@test.com");

        String token = jwtService.generateToken(user);

        String email = jwtService.extractEmail(token);

        assertEquals("test@test.com", email);
    }

    @Test
    void shouldValidateValidToken() {

        UserEntity user = new UserEntity();
        user.setEmail("test@test.com");

        String token = jwtService.generateToken(user);

        boolean valid = jwtService.isTokenValid(token);

        assertTrue(valid);
    }

    @Test
    void shouldReturnFalseForInvalidToken() {

        String invalidToken = "invalid.jwt.token";

        boolean valid = jwtService.isTokenValid(invalidToken);

        assertFalse(valid);
    }

    @Test
    void shouldReturnFalseForMalformedToken() {

        String badToken = "abcd";

        boolean valid = jwtService.isTokenValid(badToken);

        assertFalse(valid);
    }
}