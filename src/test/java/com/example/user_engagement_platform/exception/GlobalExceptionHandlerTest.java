package com.example.user_engagement_platform.exception;

import com.example.user_engagement_platform.dto.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleUserExists() {
        UserAlreadyExistsException ex = new UserAlreadyExistsException("User exists");

        ResponseEntity<ApiResponse<Object>> response = handler.handleUserExists(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("failed", response.getBody().getStatus());
        assertEquals("User exists", response.getBody().getMessage());
    }

    @Test
    void testHandleUserNotFound() {
        UserNotFoundException ex = new UserNotFoundException("User not found");

        ResponseEntity<ApiResponse<Object>> response = handler.handleUserNotFound(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("failed", response.getBody().getStatus());
        assertEquals("User not found", response.getBody().getMessage());
    }

    @Test
    void testHandleActivityNotFound() {
        ActivityNotFoundException ex = new ActivityNotFoundException("Activity missing");

        ResponseEntity<ApiResponse<Object>> response = handler.ActivityNotFound(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("failed", response.getBody().getStatus());
        assertEquals("Activity missing", response.getBody().getMessage());
    }
}