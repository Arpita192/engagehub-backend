package com.example.user_engagement_platform.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefreshTokenRequestTest {

    @Test
    void shouldCreateObjectUsingNoArgsConstructor() {

        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setRefreshToken("test-refresh-token");

        assertEquals("test-refresh-token", request.getRefreshToken());
    }

    @Test
    void shouldCreateObjectUsingAllArgsConstructor() {

        RefreshTokenRequest request =
                new RefreshTokenRequest("test-refresh-token");

        assertEquals("test-refresh-token", request.getRefreshToken());
    }
}