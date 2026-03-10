package com.example.user_engagement_platform.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DialerDtoRequestTest {

    @Test
    void testDialerDtoRequest() {
        DialerDtoRequest dto = new DialerDtoRequest();
        dto.setActivityName("Login");
        assertEquals("Login", dto.getActivityName());

        DialerDtoRequest dto2 = new DialerDtoRequest("Register");
        assertEquals("Register", dto2.getActivityName());

        DialerDtoRequest dto3 = DialerDtoRequest.builder()
                .activityName("Logout")
                .build();
        assertEquals("Logout", dto3.getActivityName());
    }
}