package com.example.user_engagement_platform.dto;

import com.example.user_engagement_platform.enums.PromotionConsent;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ConsentRequestTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldFailWhenPromotionConsentIsNull() {

        ConsentRequest request = new ConsentRequest();

        Set<ConstraintViolation<ConsentRequest>> violations =
                validator.validate(request);

        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldPassWhenPromotionConsentIsProvided() {

        ConsentRequest request = new ConsentRequest();
        request.setPromotionConsent(PromotionConsent.YES);

        Set<ConstraintViolation<ConsentRequest>> violations =
                validator.validate(request);

        assertTrue(violations.isEmpty());
    }
}