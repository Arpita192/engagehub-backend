package com.example.user_engagement_platform.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RegisterRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @MethodSource("provideTestCases")
    void shouldValidateRegisterRequest(String name,
                                       String email,
                                       String password,
                                       String mobileNumber,
                                       boolean expectedValid) {

        RegisterRequest request = RegisterRequest.builder()
                .name(name)
                .email(email)
                .password(password)
                .mobileNumber(mobileNumber)
                .build();

        Set<ConstraintViolation<RegisterRequest>> violations =
                validator.validate(request);

        if (expectedValid) {
            assertTrue(violations.isEmpty());
        } else {
            assertFalse(violations.isEmpty());
        }
    }

    static Stream<Arguments> provideTestCases() {
        return Stream.of(
                Arguments.of("Arpita", "arpita@test.com", "password123", "9876543210", true),

                Arguments.of("", "arpita@test.com", "password123", "9876543210", false),

                Arguments.of("arpita", "invalid-email", "password123", "9876543210", false),

                Arguments.of("arpita", "arpita@test.com", "123", "9876543210", false),

                Arguments.of("arpita", "arpita@test.com", "password123", "1234567890", false)
        );
    }
}