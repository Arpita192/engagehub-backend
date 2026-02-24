package com.example.user_engagement_platform.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {

    @Test
    void shouldCreateApiResponseUsingAllArgsConstructor() {
        ApiResponse<String> response =
                new ApiResponse<>("SUCCESS", "OK", "DATA");

        assertEquals("SUCCESS", response.getStatus());
        assertEquals("OK", response.getMessage());
        assertEquals("DATA", response.getData());
    }

    @Test
    void shouldSetAndGetValuesUsingSetters() {
        ApiResponse<Integer> response = new ApiResponse<>();

        response.setStatus("ERROR");
        response.setMessage("Something went wrong");
        response.setData(400);

        assertEquals("ERROR", response.getStatus());
        assertEquals("Something went wrong", response.getMessage());
        assertEquals(400, response.getData());
    }
}
