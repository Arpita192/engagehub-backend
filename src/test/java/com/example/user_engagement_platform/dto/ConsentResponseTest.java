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
                .channel("SMS")
                .status(1)
                .updatedAt(now)
                .build();

        assertEquals(1L, response.getUserId());
        assertEquals("SMS", response.getChannel());
        assertEquals(1, response.getStatus());
        assertEquals(now, response.getUpdatedAt());
    }
}