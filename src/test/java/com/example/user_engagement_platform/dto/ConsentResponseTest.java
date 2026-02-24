package com.example.user_engagement_platform.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ConsentResponseTest {

    @Test
    void shouldBuildConsentResponseSuccessfully() {

        LocalDateTime now = LocalDateTime.now();

        ConsentResponse response = ConsentResponse.builder()
                .userId(1L)
                .name("Arpita")
                .email("arpita@test.com")
                .mobileNumber("9876543210")
                .channel("SMS")
                .status(1)
                .updatedAt(now)
                .build();

        assertEquals(1L, response.getUserId());
        assertEquals("Arpita", response.getName());
        assertEquals("arpita@test.com", response.getEmail());
        assertEquals("9876543210", response.getMobileNumber());
        assertEquals("SMS", response.getChannel());
        assertEquals(1, response.getStatus());
        assertEquals(now, response.getUpdatedAt());
    }
}