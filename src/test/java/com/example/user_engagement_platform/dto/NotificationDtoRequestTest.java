package com.example.user_engagement_platform.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NotificationDtoRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidNotificationDtoRequest() {
        NotificationDtoRequest dto = NotificationDtoRequest.builder()
                .activityName("Onboarding")
                .channel("SMS")
                .build();

        Set<ConstraintViolation<NotificationDtoRequest>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());

        dto.setChannel("EMAIL");
        violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidChannel() {
        NotificationDtoRequest dto = NotificationDtoRequest.builder()
                .activityName("Onboarding")
                .channel("PUSH")
                .build();

        Set<ConstraintViolation<NotificationDtoRequest>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals("Channel must be either SMS or EMAIL",
                violations.iterator().next().getMessage());
    }

    @Test
    void testBlankChannel() {
        NotificationDtoRequest dto = NotificationDtoRequest.builder()
                .activityName("Onboarding")
                .channel("")
                .build();

        Set<ConstraintViolation<NotificationDtoRequest>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals("Channel is required",
                violations.iterator().next().getMessage());
    }
}