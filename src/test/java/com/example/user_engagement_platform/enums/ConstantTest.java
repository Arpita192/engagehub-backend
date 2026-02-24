package com.example.user_engagement_platform.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstantTest {


    @Test
    void shouldContainAllEnumValues() {
        Constant[] values = Constant.values();

        assertEquals(2, values.length);
        assertEquals(Constant.USER, values[0]);
        assertEquals(Constant.ACTIVE, values[1]);
    }

}