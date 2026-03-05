package com.example.user_engagement_platform.dto;

import com.example.user_engagement_platform.enums.PromotionConsent;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ConsentResponseTest {

    @Test
    void shouldCreateConsentResponseUsingBuilder() {

        LocalDateTime now = LocalDateTime.now();

        ConsentResponse response = ConsentResponse.builder()
                .consentId(10L)
                .userId(1L)
                .promotionConsent(PromotionConsent.YES)
                .explicitConsent(now)
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertNotNull(response);
        assertEquals(10L, response.getConsentId());
        assertEquals(1L, response.getUserId());
        assertEquals(PromotionConsent.YES, response.getPromotionConsent());
        assertEquals(now, response.getExplicitConsent());
        assertEquals(now, response.getCreatedAt());
        assertEquals(now, response.getUpdatedAt());
    }
}