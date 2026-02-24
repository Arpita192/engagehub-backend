package com.example.user_engagement_platform.entity;

import com.example.user_engagement_platform.enums.Constant;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    private static UserEntity user;
    private static LocalDateTime now;

    @BeforeAll
    static void setup() {
        now = LocalDateTime.now();

        user = new UserEntity();
        user.setId(1L);
        user.setName("Arpita");
        user.setMobileNumber("9876543210");
        user.setEmail("Arpita@test.com");
        user.setPassword("password");
        user.setRole(Constant.USER);
        user.setStatus(Constant.ACTIVE);
        user.setCreatedAt(now);
    }

    @Test
    void shouldSetRequestInDb() {
        assertEquals(1L, user.getId());
        assertEquals("Arpita", user.getName());
        assertEquals("9876543210", user.getMobileNumber());
        assertEquals("Arpita@test.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals(Constant.USER, user.getRole());
        assertEquals(Constant.ACTIVE, user.getStatus());
        assertEquals(now, user.getCreatedAt());
    }

    @Test
    void retrieveData() {
        assertNotNull(user);
        assertEquals("Arpita", user.getName());
        assertEquals("Arpita@test.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals("9876543210", user.getMobileNumber());
        assertEquals(1L, user.getId());
        assertEquals(Constant.USER, user.getRole());
        assertEquals(Constant.ACTIVE, user.getStatus());
    }
}