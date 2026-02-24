package com.example.user_engagement_platform.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginResponseTest {

    @Test
    void shouldCreateLoginResponse() {
        LoginResponse response = new LoginResponse(
                "token123",
                "Bearer",
                "arpita",
                "9876543210",
                "arpita@test.com"
        );

        assertEquals("token123", response.getAccessToken());
        assertEquals("Bearer", response.getTokenType());
        assertEquals("arpita", response.getName());
        assertEquals("9876543210", response.getMobileNumber());
        assertEquals("arpita@test.com", response.getEmail());
    }

}
