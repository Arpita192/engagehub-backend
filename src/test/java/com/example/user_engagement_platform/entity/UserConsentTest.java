package com.example.user_engagement_platform.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserConsentTest {

    private static UserConsent consent;
    private static UserEntity user;
    private static LocalDateTime now;

    @BeforeAll
    static void setup() {

        now = LocalDateTime.now();

        user = new UserEntity();
        user.setId(1L);
        user.setName("Arpita");
        user.setEmail("arpita@test.com");

        consent = new UserConsent();
        consent.setId(10L);
        consent.setUser(user);
        consent.setChannel("EMAIL");
        consent.setStatus(1);
        consent.setUpdatedAt(now);
    }

    @Test
    void shouldSetRequestInDb() {

        assertEquals(10L, consent.getId());
        assertEquals(user, consent.getUser());
        assertEquals("EMAIL", consent.getChannel());
        assertEquals(1, consent.getStatus());
        assertEquals(now, consent.getUpdatedAt());
    }

    @Test
    void retrieveData() {

        assertNotNull(consent);
        assertEquals("EMAIL", consent.getChannel());
        assertEquals(1, consent.getStatus());
        assertEquals(now, consent.getUpdatedAt());

        assertNotNull(consent.getUser());
        assertEquals("Arpita", consent.getUser().getName());
        assertEquals("arpita@test.com", consent.getUser().getEmail());
    }

    @Test
    void shouldHaveDefaultValuesWhenNewObjectCreated() {

        UserConsent newConsent = new UserConsent();

        assertEquals("NOT_DECIDED", newConsent.getChannel());
        assertEquals(0, newConsent.getStatus());
    }
}