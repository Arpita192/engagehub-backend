package com.example.user_engagement_platform.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefreshTokenResponseTest {

    @Test
    void shouldCreateObjectUsingNoArgsConstructor() {

        RefreshTokenResponse response = new RefreshTokenResponse();
        response.setAccessToken("access-token");
        response.setRefreshToken("refresh-token");

        assertEquals("access-token", response.getAccessToken());
        assertEquals("refresh-token", response.getRefreshToken());
    }

    @Test
    void shouldCreateObjectUsingAllArgsConstructor() {

        RefreshTokenResponse response =
                new RefreshTokenResponse("access-token", "refresh-token");

        assertEquals("access-token", response.getAccessToken());
        assertEquals("refresh-token", response.getRefreshToken());
    }
}