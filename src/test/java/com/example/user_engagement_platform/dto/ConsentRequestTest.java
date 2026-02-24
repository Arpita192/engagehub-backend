package com.example.user_engagement_platform.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

class ConsentRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @MethodSource("provideConsentRequests")
    void shouldValidateConsentRequest(
            Integer status,
            String channel,
            boolean expectedValid
    ) {
        ConsentRequest request = new ConsentRequest();
        request.setStatus(status);
        request.setChannel(channel);

        Set<ConstraintViolation<ConsentRequest>> violations =
                validator.validate(request);

        if (expectedValid) {
            assertTrue(violations.isEmpty());
        } else {
            assertFalse(violations.isEmpty());
        }
    }

    static Stream<Arguments> provideConsentRequests() {
        return Stream.of(
                Arguments.of(2, "SMS", true),
                Arguments.of(null, "EMAIL", false),
                Arguments.of(-1, "SMS", false),
                Arguments.of(3, "SMS", false),
                Arguments.of(1, "WHATSAPP", false),
                Arguments.of(1, "", false)
        );
    }
}