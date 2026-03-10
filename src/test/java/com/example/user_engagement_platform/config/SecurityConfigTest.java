package com.example.user_engagement_platform.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class SecurityConfigTest {

    private final SecurityConfig securityConfig = new SecurityConfig(null);

    @Test
    void passwordEncoderShouldEncodeAndMatchPassword() {

        PasswordEncoder encoder = securityConfig.passwordEncoder();

        String rawPassword = "secret123";
        String encodedPassword = encoder.encode(rawPassword);

        assertNotNull(encodedPassword);
        assertNotEquals(rawPassword, encodedPassword);
        assertTrue(encoder.matches(rawPassword, encodedPassword));
    }
}