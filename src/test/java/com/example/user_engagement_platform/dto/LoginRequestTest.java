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

class LoginRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @MethodSource("provideLoginRequests")
    void shouldValidateLoginRequest(String email,
                                    String password,
                                    boolean expectedValid) {

        LoginRequest request = LoginRequest.builder()
                .email(email)
                .password(password)
                .build();

        Set<ConstraintViolation<LoginRequest>> violations =
                validator.validate(request);

        assertEquals(expectedValid, violations.isEmpty());
    }

    static Stream<Arguments> provideLoginRequests() {
        return Stream.of(
                Arguments.of("user@test.com", "password123", true),   // valid
                Arguments.of("", "password123", false),               // blank email
                Arguments.of("invalid-email", "password123", false),  // invalid email
                Arguments.of("user@test.com", "", false),             // blank password
                Arguments.of("user@test.com", "123", false)           // short password
        );
    }
}