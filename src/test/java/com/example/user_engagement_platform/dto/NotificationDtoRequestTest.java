package com.example.user_engagement_platform.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotificationDtoRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenChannelIsValid_thenNoViolations() {
        NotificationDtoRequest dto = NotificationDtoRequest.builder()
                .activityName("User Signup")
                .channel("SMS")
                .build();

        Set<ConstraintViolation<NotificationDtoRequest>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "No violations expected for valid channel");
    }


    @Test
    void whenChannelIsInvalid_thenPatternViolationOccurs() {
        NotificationDtoRequest dto = NotificationDtoRequest.builder()
                .activityName("User Signup")
                .channel("PUSH")
                .build();

        Set<ConstraintViolation<NotificationDtoRequest>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertEquals("Channel must be either SMS or EMAIL", violations.iterator().next().getMessage());
    }

    @Test
    void whenActivityNameIsNull_thenNoViolationSinceNotRequired() {
        NotificationDtoRequest dto = NotificationDtoRequest.builder()
                .activityName(null)
                .channel("EMAIL")
                .build();

        Set<ConstraintViolation<NotificationDtoRequest>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "activityName is optional, no violations expected");
    }
}