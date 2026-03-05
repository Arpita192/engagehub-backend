package com.example.user_engagement_platform.entity;

import com.example.user_engagement_platform.enums.PromotionConsent;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserConsentTest {

    @Test
    void shouldCreateUserConsentUsingBuilder() {

        UserEntity user = new UserEntity();
        user.setId(1L);

        LocalDateTime now = LocalDateTime.now();

        UserConsent consent = UserConsent.builder()
                .id(10L)
                .user(user)
                .promotionConsent(PromotionConsent.YES)
                .explicitConsent(now)
                .build();

        assertNotNull(consent);
        assertEquals(10L, consent.getId());
        assertEquals(user, consent.getUser());
        assertEquals(PromotionConsent.YES, consent.getPromotionConsent());
        assertEquals(now, consent.getExplicitConsent());
    }

    @Test
    void shouldSetAndGetFields() {

        UserConsent consent = new UserConsent();

        UserEntity user = new UserEntity();
        user.setId(1L);

        consent.setId(20L);
        consent.setUser(user);
        consent.setPromotionConsent(PromotionConsent.NO);

        assertEquals(20L, consent.getId());
        assertEquals(user, consent.getUser());
        assertEquals(PromotionConsent.NO, consent.getPromotionConsent());
    }
}