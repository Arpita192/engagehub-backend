package com.example.user_engagement_platform.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PromotionConsentTest {

    @Test
    void shouldContainAllEnumValues() {
        PromotionConsent[] values = PromotionConsent.values();

        assertEquals(2, values.length);
        assertEquals(PromotionConsent.YES, values[0]);
        assertEquals(PromotionConsent.NO, values[1]);
    }
}