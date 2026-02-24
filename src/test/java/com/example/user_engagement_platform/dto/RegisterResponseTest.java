package com.example.user_engagement_platform.dto;

import com.example.user_engagement_platform.enums.Constant;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RegisterResponseTest {

    @Test
    void shouldCreateRegisterResponse() {
        LocalDateTime now = LocalDateTime.now();

        RegisterResponse response = RegisterResponse.builder()
                .id(2L)
                .name("Alice")
                .email("alice@test.com")
                .mobileNumber("9999999999")
                .role(Constant.USER)
                .status(Constant.ACTIVE)
                .createdAt(now)
                .build();

        assertEquals("Alice", response.getName());
        assertEquals(Constant.USER, response.getRole());
        assertEquals(Constant.ACTIVE, response.getStatus());
    }
}
