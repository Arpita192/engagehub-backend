package com.example.user_engagement_platform.dto;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class UserCacheDtoTest {

    @Test
    void shouldCreateObjectUsingNoArgsConstructorAndSetters() {

        UserCacheDto dto = new UserCacheDto();

        dto.setId(1L);
        dto.setName("Arpita");
        dto.setEmail("arpita@gmail.com");
        dto.setMobileNumber("9999999999");
        dto.setRole("USER");
        dto.setStatus("ACTIVE");

        assertEquals(1L, dto.getId());
        assertEquals("Arpita", dto.getName());
        assertEquals("arpita@gmail.com", dto.getEmail());
        assertEquals("9999999999", dto.getMobileNumber());
        assertEquals("USER", dto.getRole());
        assertEquals("ACTIVE", dto.getStatus());
    }

    @Test
    void shouldCreateObjectUsingAllArgsConstructor() {

        UserCacheDto dto = new UserCacheDto(
                1L,
                "Arpita",
                "arpita@gmail.com",
                "9999999999",
                "USER",
                "ACTIVE"
        );

        assertEquals(1L, dto.getId());
        assertEquals("Arpita", dto.getName());
        assertEquals("arpita@gmail.com", dto.getEmail());
        assertEquals("9999999999", dto.getMobileNumber());
        assertEquals("USER", dto.getRole());
        assertEquals("ACTIVE", dto.getStatus());
    }
}